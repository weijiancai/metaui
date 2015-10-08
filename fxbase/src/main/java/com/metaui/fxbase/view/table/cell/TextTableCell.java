package com.metaui.fxbase.view.table.cell;

import com.metaui.core.dict.EnumAlign;
import com.metaui.core.meta.MetaDataType;
import com.metaui.fxbase.view.table.column.BaseTableColumn;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 文本 Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TextTableCell extends BaseTableCell {
    private Label label;
    private TextField textField;

    private String oldValue;

    public TextTableCell(BaseTableColumn column, TableFieldModel model) {
        super(column, model);
        label = new Label();
        if (EnumAlign.CENTER == model.getAlign() || MetaDataType.INTEGER == model.getDataType()) {
            this.setAlignment(Pos.CENTER);
        } else if (EnumAlign.RIGHT == model.getAlign()) {
            this.setAlignment(Pos.CENTER_RIGHT);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        label.setText(item);
        setGraphic(label);
    }

    @Override
    public void startEdit() {
        if (!model.getEditable()) {
            return;
        }
        if (textField == null) {
            textField = new TextField();
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        isModified.set(true);
                        commitEdit(textField.getText());
                        valueProperty.set(textField.getText());
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        isModified.set(false);
                        cancelEdit();
                    }
                }
            });
        }
        oldValue = getItem();
        textField.setText(getItem());

        super.startEdit();
        setGraphic(textField);
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setItem(oldValue);
        label.setText(oldValue);
        setGraphic(label);
    }
}
