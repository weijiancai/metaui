package com.metaui.fxbase.view.table.cell;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.ui.layout.property.TableFieldProperty;
import com.metaui.core.util.Callback;
import com.metaui.core.util.UString;
import com.metaui.fxbase.ui.component.table.event.TableCellRenderEvent;
import com.metaui.fxbase.ui.event.DataChangeEvent;
import com.metaui.fxbase.view.dialog.MUDialog;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.column.BaseTableColumn;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTableCell extends TableCell<DataMap, String> {
    protected BaseTableColumn tableColumn;
    protected TableFieldModel model;
    protected BooleanProperty isModified = new SimpleBooleanProperty(false);
    protected StringProperty valueProperty = new SimpleStringProperty();

    public BaseTableCell(BaseTableColumn column, final TableFieldModel model) {
        this.tableColumn = column;
        this.model = model;

        // 对齐方式
        model.alignProperty().addListener((observable, oldValue, newValue) -> {
            Pos align = Pos.CENTER_LEFT;
            if (EnumAlign.CENTER == model.getAlign()) {
                align = Pos.CENTER;
            } else if (EnumAlign.RIGHT == model.getAlign()) {
                align = Pos.CENTER_RIGHT;
            }

            setAlignment(align);
        });
        valueProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue == null || !oldValue.equals(newValue)) {
                    /*DataChangeEvent event = new DataChangeEvent(model, model.getName(), getItem());
                    event.setRowData((DataMap) getTableRow().getItem());
                    fireEvent(event);*/
                    ((DataMap) getTableRow().getItem()).put(model.getName(), newValue);
                }
            }
        });
        this.setTextOverrun(OverrunStyle.ELLIPSIS);
        this.setPrefHeight(25);
        this.setMaxHeight(30);
    }

    public BaseTableCell() {
        super();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (tableColumn != null && !empty) {
            Callback<TableCellRenderEvent> onCellRender = tableColumn.getTable().getOnCellRender();
            if (onCellRender != null) {
                MUTable table = tableColumn.getTable();
                TableCellRenderEvent event = new TableCellRenderEvent();
                event.setTable(table);
                event.setColumn(tableColumn);
                event.setCell(this);
                event.setValue(item);
                event.setRowData(table.getItems().get(this.getTableRow().getIndex()));
                try {
                    onCellRender.call(event);
                } catch (Exception e) {
                    MUDialog.showException(e);
                }
            }
        }
    }
}
