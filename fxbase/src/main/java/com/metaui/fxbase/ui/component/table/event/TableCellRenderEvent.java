package com.metaui.fxbase.ui.component.table.event;

import com.metaui.core.datasource.DataMap;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.cell.BaseTableCell;
import com.metaui.fxbase.view.table.column.BaseTableColumn;

/**
 * 单元格渲染事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableCellRenderEvent {
    private MUTable table;
    private BaseTableColumn column;
    private BaseTableCell cell;
    private String value;
    private DataMap rowData;

    public MUTable getTable() {
        return table;
    }

    public void setTable(MUTable table) {
        this.table = table;
    }

    public BaseTableColumn getColumn() {
        return column;
    }

    public void setColumn(BaseTableColumn column) {
        this.column = column;
    }

    public BaseTableCell getCell() {
        return cell;
    }

    public void setCell(BaseTableCell cell) {
        this.cell = cell;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataMap getRowData() {
        return rowData;
    }

    public void setRowData(DataMap rowData) {
        this.rowData = rowData;
    }
}
