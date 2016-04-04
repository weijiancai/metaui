package com.metaui.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 写入excel文件
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/26.
 */
public class ExcelWriter {
    private File outFile;
    private Workbook wb;
    private WriterCallback callback;

    public ExcelWriter(File outFile) {
        this.outFile = outFile;
        wb = getWorkbook();
    }

    public WriterCallback getCallback() {
        return callback;
    }

    public void setCallback(WriterCallback callback) {
        this.callback = callback;
    }

    public void writeData(ITableData tableData) throws IOException {
        Sheet sheet = createSheet("Sheet 1");
        sheet.autoSizeColumn(1); // 自适应宽度

        List<TableHeader> headers = tableData.getHeaders();
        // 表头行 48pt font
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(12.75f);
        CellStyleFactory styleFactory = CellStyleFactory.getInstance(wb);
        CellStyle headerStyle = styleFactory.createTableHeader();

        for (int i = 0; i < headers.size(); i++) {
            TableHeader header = headers.get(i);
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header.getDisplayName());
            cell.setCellStyle(headerStyle);

            // 设置宽度
            int width = header.getWidth();
            if (width > 0) {
                // 这里的宽度是按字符算的，width是像素，所以这里这里除于7
                sheet.setColumnWidth(i, 256 * width / 7);
            }
        }

        // 写入数据
        Row row;
        Cell cell;
        int rowNum = 1;
        for (Map map : tableData.getData()) {
            row = sheet.createRow(rowNum);

            for (int i = 0; i < headers.size(); i++) {
                TableHeader header = headers.get(i);
                cell = row.createCell(i);
                setCellData(cell, header, map);
                // 调用回掉
                if (callback != null) {
                    callback.onCell(header, cell, map);
                }
            }

            // 调用回掉
            if (callback != null) {
                callback.onRow(row, map);
            }

            rowNum++;
        }

        // 统计行：求和
        Row totalRow = sheet.createRow(rowNum);
        for (int i = 0; i < headers.size(); i++) {
            TableHeader header = headers.get(i);
            if (header.isSum()) {
                cell = totalRow.createCell(i);
                cell.setCellFormula(String.format("sum(%s%d:%1$s%d)", (char)('A' + i), 2, rowNum));
            }
        }

        // 写入文件
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        FileOutputStream out = new FileOutputStream(outFile);
        wb.write(out);
        out.close();

        wb.close();
    }

    private void setCellData(Cell cell, TableHeader header, Map map) {
        Class<?> type = header.getType();

        Object obj = map.get(header.getName());
        if (obj != null) {
            // 整数
            if (type == int.class) {
                cell.setCellValue(Integer.parseInt(obj.toString()));
            } else if (type == double.class) { // 小数
                cell.setCellValue(Double.parseDouble(obj.toString()));
            }  else if (type == Date.class) { // 日期类型
                cell.setCellValue((Date)obj);
                CellStyle style = wb.createCellStyle();
                style.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                cell.setCellStyle(style);
            }else {
                cell.setCellValue(obj.toString());
            }
            // 设置单元格样式
            if (header.getStyle() != null) {
                cell.setCellStyle(header.getStyle());
            }
        }
    }

    public Workbook getWorkbook() {
        if (this.wb == null) {
            wb = new HSSFWorkbook();
        }

        return wb;
    }

    public Sheet createSheet(String sheetName) {
        Sheet sheet = wb.createSheet(sheetName);

        //turn off gridlines
        sheet.setDisplayGridlines(true);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        return sheet;
    }
}
