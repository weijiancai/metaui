package com.metaui.fxbase.view.table.cell;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.ui.layout.property.TableFieldProperty;
import com.metaui.fxbase.ui.event.DataChangeEvent;
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
    protected TableColumn<DataMap, String> tableColumn;
    protected TableFieldModel model;
    protected BooleanProperty isModified = new SimpleBooleanProperty(false);
    protected StringProperty valueProperty = new SimpleStringProperty();

    public BaseTableCell(TableColumn<DataMap, String> column, final TableFieldModel model) {
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
                /*if (oldValue == null || !oldValue.equals(newValue)) {
                    DataChangeEvent event = new DataChangeEvent(model, model.getName(), getItem());
                    event.setRowData((DataMap) getTableRow().getItem());
                    fireEvent(event);
                }*/
            }
        });
        this.setTextOverrun(OverrunStyle.ELLIPSIS);
        this.setPrefHeight(25);
        this.setMaxHeight(30);
    }

    public BaseTableCell() {
        super();
    }
}
