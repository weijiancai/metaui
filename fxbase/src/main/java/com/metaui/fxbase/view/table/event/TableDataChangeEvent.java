package com.metaui.fxbase.view.table.event;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.ui.layout.property.BaseProperty;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * 数据改变事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableDataChangeEvent extends Event {
    public static EventType<TableDataChangeEvent> EVENT_TYPE = new EventType<>("TABLE_DATA_CHANGE_EVENT_TYPE");
    private TableFieldModel model;
    private String name;
    private String newValue;
    private DataMap rowData;

    public TableDataChangeEvent(TableFieldModel model, String name, String newValue) {
        super(EVENT_TYPE);

        this.model = model;
        this.name = name;
        this.newValue = newValue;
    }

    public TableFieldModel getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getNewValue() {
        return newValue;
    }

    public DataMap getRowData() {
        return rowData;
    }

    public void setRowData(DataMap rowData) {
        this.rowData = rowData;
    }
}
