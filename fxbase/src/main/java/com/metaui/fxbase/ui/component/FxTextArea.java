package com.metaui.fxbase.ui.component;

import com.metaui.core.meta.DisplayStyle;
import com.metaui.fxbase.ui.config.FxFormFieldConfig;
import com.metaui.fxbase.ui.event.FxLayoutEvent;
import com.metaui.fxbase.ui.view.FxFormField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * JavaFx 文本输入域
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxTextArea extends FxFormField {
    private TextArea textArea;

    public FxTextArea(FxFormFieldConfig fieldConfig) {
        super(fieldConfig);

        textArea = new TextArea();
        textArea.setPrefHeight(fieldConfig.getHeight());
    }

    @Override
    public void setDisplayStyle(DisplayStyle displayStyle) {
    }

    @Override
    public Node getNode() {
        return textArea;
    }

    @Override
    public void setValue(String... values) {
        textArea.setText(values[0]);
    }

    @Override
    public String getValue() {
        return textArea.getText().trim();
    }

    @Override
    public String[] getValues() {
        return new String[]{getValue()};
    }

    @Override
    public StringProperty textProperty() {
        return textArea.textProperty();
    }

    @Override
    public DoubleProperty widthProperty() {
        return textArea.prefWidthProperty();
    }

    @Override
    public DoubleProperty heightProperty() {
        return textArea.prefHeightProperty();
    }

    @Override
    public void registLayoutEvent() {
        textArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textArea.fireEvent(new FxLayoutEvent(fieldConfig.getLayoutConfig(), FxTextArea.this));
            }
        });
    }
}
