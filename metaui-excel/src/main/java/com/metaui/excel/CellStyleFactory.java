package com.metaui.excel;

import org.apache.poi.ss.usermodel.*;

/**
 * 单元格样式工厂
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/29.
 */
public class CellStyleFactory {
    private static CellStyleFactory factory;
    private Workbook wb;

    private CellStyleFactory() {
    }

    public static CellStyleFactory getInstance(Workbook wb) {
        if (factory == null) {
            factory = new CellStyleFactory();
        }
        factory.setWorkbook(wb);

        return factory;
    }

    public void setWorkbook(Workbook wb) {
        this.wb = wb;
    }

    /**
     * 创建表头样式
     *
     * @return
     */
    public CellStyle createTableHeader() {
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle style = createBorderedStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);

        return style;
    }

    private CellStyle createBorderedStyle(){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    public CellStyle fillRed(Cell cell) {
        return fillColor(cell, IndexedColors.RED.getIndex());
    }

    public CellStyle fillGreen(Cell cell) {
        return fillColor(cell, IndexedColors.GREEN.getIndex());
    }

    public CellStyle fillColor(short color) {
        return fillColor(null, color);
    }

    public CellStyle fillColor(Cell cell, short color) {
        CellStyle style = createBorderedStyle();
        if (cell != null) {
            style.cloneStyleFrom(cell.getCellStyle());
        }
        style.setFillForegroundColor(color);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return style;
    }
}
