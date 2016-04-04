package com.metaui.excel;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/26.
 */
public class ExcelWriterTest {
    private Logger log = LoggerFactory.getLogger(ExcelWriterTest.class);

    @Test
    public void testWriteListData() throws IOException {
        File out = new File("D:/text.xls");

        List<TableHeader> headers = new ArrayList<TableHeader>();
        TableHeader shippingCode = new TableHeader("shippingCode", "运单号", 100);
        TableHeader packAmount = new TableHeader("packageAmount", "件数", int.class, 60);
        packAmount.setSum(true);
        headers.add(packAmount);
        headers.add(new TableHeader("arrive", "到站", 80));
        headers.add(new TableHeader("diffType", "状态", 50));
        headers.add(new TableHeader("date", "发货日期", Date.class, 120));
        headers.add(shippingCode);

        List<Map> data = new ArrayList<Map>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageAmount", 5);
        map.put("arrive", "上海");
        map.put("diffType", "equal");
        map.put("date", new Date());
        map.put("shippingCode", "28KDB323432");
        data.add(map);

        DefaultTableData tableData = new DefaultTableData(headers, data);

        ExcelWriter writer = new ExcelWriter(out);

        final CellStyleFactory styleFactory = CellStyleFactory.getInstance(writer.getWorkbook());
        shippingCode.setStyle(styleFactory.fillGreen(null));

        writer.setCallback(new WriterCallback() {
            @Override
            public void onRow(Row row, Map rowData) {

            }

            @Override
            public void onCell(TableHeader header, Cell cell, Map rowData) {
                /*if ("shippingCode".equals(header.getName())) {
                    cell.setCellStyle(CellStyleFactory.fillGreen(cell));
                } else {
                    cell.setCellStyle(CellStyleFactory.fillRed(cell));
                }*/
//                cell.setCellStyle(styleFactory.fillRed(cell));
            }
        });
        writer.writeData(tableData);

        /*File file = new File("C:\\Users\\weiji\\Downloads\\日志\\508001\\001_all_localhost_2016-03-24.log");
        print(file);
        log.info("测试");*/
    }

    private void print(File file) throws IOException {
        System.out.println("===========================");
        System.out.println("file = " + file.getAbsolutePath());
        List<String> list = FileUtils.readLines(file, "GBK");
        int i = 0;
        for (String str : list) {
            i++;
            if (str.contains("39260") || str.contains("39262")) {
                System.out.println("line = " + i + " ; " + str);
            }
        }
    }
}