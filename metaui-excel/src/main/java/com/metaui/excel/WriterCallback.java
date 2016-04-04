package com.metaui.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;

/**
 * 写入数据时，调用此回掉接口
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/29.
 */
public interface WriterCallback {
    /**
     * 创建行时回掉此函数
     *
     * @param row 行
     * @param rowData 行数据
     */
    void onRow(Row row, Map rowData);

    /**
     * 创建单元格时回掉此函数
     *
     * @param header 头信息
     * @param cell 单元格
     * @param rowData 行数据
     */
    void onCell(TableHeader header, Cell cell, Map rowData);
}
