package com.metaui.fxbase.ui.component;

import com.metaui.core.meta.DisplayStyle;
import com.metaui.fxbase.ui.config.FxFormFieldConfig;
import com.metaui.fxbase.ui.event.FxLayoutEvent;
import com.metaui.fxbase.ui.view.FxFormField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * JavaFx文本输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxTextField extends FxFormField {
    private TextField textField;
    private FxFormFieldConfig fieldConfig;

    public FxTextField(final FxFormFieldConfig fieldConfig) {
        super(fieldConfig);
        this.fieldConfig = fieldConfig;

        textField = new TextField();
        textField.setPrefWidth(fieldConfig.getWidth());
        textField.setText(fieldConfig.getValue());
    }

    @Override
    public void setDisplayStyle(DisplayStyle displayStyle) {
    }

    @Override
    public Node getNode() {
        return textField;
    }

    @Override
    public void setValue(String... values) {
        textField.setText(values[0]);
    }

    @Override
    public String getValue() {
        return textField.getText().trim();
    }

    @Override
    public String[] getValues() {
        return new String[]{getValue()};
    }

    @Override
    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @Override
    public DoubleProperty widthProperty() {
        return textField.prefWidthProperty();
    }

    @Override
    public DoubleProperty heightProperty() {
        return textField.prefHeightProperty();
    }

    @Override
    public void registLayoutEvent() {
        textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textField.fireEvent(new FxLayoutEvent(fieldConfig.getLayoutConfig(), FxTextField.this));
            }
        });
    }
}
