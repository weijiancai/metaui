package com.metaui.excel;

import java.util.List;
import java.util.Map;

/**
 * Excel表格数据
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/26.
 */
public interface ITableData {

    /**
     * 获得表头信息列表
     *
     * @return 返回表头列表
     */
    List<TableHeader> getHeaders();

    /**
     * 设置表头信息列表
     *
     * @param headers 表头信息列表
     */
    void setHeaders(List<TableHeader> headers);

    /**
     * 获得表格数据列表
     *
     * @return 返回表格数据列表
     */
    List<? extends Map> getData();

    /**
     * 设置表格数据列表
     *
     * @param data 表格数据列表
     */
    void setData(List<? extends Map> data);
}
