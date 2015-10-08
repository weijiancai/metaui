package com.metaui.fxbase.view.table.column;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.util.UDate;
import com.metaui.core.util.UObject;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.table.cell.BooleanTableCell;
import com.metaui.fxbase.view.table.cell.DictTableCell;
import com.metaui.fxbase.view.table.cell.HyperlinkTableCell;
import com.metaui.fxbase.view.table.cell.TextTableCell;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Date;

/**
 * Base Table Column
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTableColumn extends TableColumn<DataMap, String> {
    protected MUTable table;
    protected TableFieldModel model;

    public BaseTableColumn(MUTable table, final TableFieldModel model) {
        this.table = table;
        this.model = model;
//        this.setText(property.getDisplayName());
//        this.setPrefWidth(property.getWidth());
        this.setMinWidth(60);
        this.idProperty().bindBidirectional(model.nameProperty());
        this.textProperty().bindBidirectional(model.displayNameProperty());
        this.prefWidthProperty().bind(model.widthProperty());
        this.visibleProperty().bind(model.displayProperty());
        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            model.setWidth(newValue.intValue());
        });

        this.setCellValueFactory(new Callback<CellDataFeatures<DataMap, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<DataMap, String> param) {
                Object obj = param.getValue().get(model.getName());
                if (obj instanceof Date) {
                    return new SimpleStringProperty(UDate.dateToString((Date)obj, "yyyy-MM-dd HH:mm:ss"));
                }
                return new SimpleStringProperty(UObject.toString(obj));
            }
        });

        this.setCellFactory(this::getTableCell);
    }

    private TableCell<DataMap, String> getTableCell(TableColumn<DataMap, String> param) {
        if (MetaDataType.BOOLEAN == model.getDataType() || DisplayStyle.BOOLEAN == model.getDisplayStyle()) {
            return new BooleanTableCell(this, model);
        }

        if(DisplayStyle.COMBO_BOX == model.getDisplayStyle()) {
            return new DictTableCell(this, model);
        }

        /*if (DisplayStyle.DATE == model.getDisplayStyle()) {

        }*/

        if (model.isFk()) {
            return new HyperlinkTableCell(this, model);
        }

        return new TextTableCell(this, model);
    }

    public MUTable getTable() {
        return table;
    }

    public TableFieldModel getModel() {
        return model;
    }
}
