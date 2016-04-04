package com.metaui.excel;

import java.util.List;
import java.util.Map;

/**
 * Excel表格数据默认实现类
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/3/29.
 */
public class DefaultTableData implements ITableData {
    private List<TableHeader> headers;
    private List<? extends Map> data;

    public DefaultTableData(List<TableHeader> headers, List<? extends Map> data) {
        this.headers = headers;
        this.data = data;
    }

    @Override
    public List<TableHeader> getHeaders() {
        return headers;
    }

    @Override
    public void setHeaders(List<TableHeader> headers) {
        this.headers = headers;
    }

    @Override
    public List<? extends Map> getData() {
        return data;
    }

    @Override
    public void setData(List<? extends Map> data) {
        this.data = data;
    }
}
