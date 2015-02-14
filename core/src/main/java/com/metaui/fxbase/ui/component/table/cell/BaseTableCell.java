package com.metaui.fxbase.ui.component.table.cell;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.ui.layout.property.TableFieldProperty;
import com.metaui.fxbase.ui.event.DataChangeEvent;
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
    protected TableFieldProperty prop;
    protected BooleanProperty isModified = new SimpleBooleanProperty(false);
    protected StringProperty valueProperty = new SimpleStringProperty();

    public BaseTableCell(TableColumn<DataMap, String> column, final TableFieldProperty prop) {
        this.tableColumn = column;
        this.prop = prop;

        // 对齐方式
        prop.alignProperty().addListener(new ChangeListener<EnumAlign>() {
            @Override
            public void changed(ObservableValue<? extends EnumAlign> observable, EnumAlign oldValue, EnumAlign newValue) {
                Pos align = Pos.CENTER_LEFT;
                if (EnumAlign.CENTER == prop.getAlign()) {
                    align = Pos.CENTER;
                } else if (EnumAlign.RIGHT == prop.getAlign()) {
                    align = Pos.CENTER_RIGHT;
                }

                setAlignment(align);
            }
        });
        valueProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue == null || !oldValue.equals(newValue)) {
                    DataChangeEvent event = new DataChangeEvent(prop, prop.getName(), getItem());
                    event.setRowData((DataMap) getTableRow().getItem());
                    fireEvent(event);
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
}
