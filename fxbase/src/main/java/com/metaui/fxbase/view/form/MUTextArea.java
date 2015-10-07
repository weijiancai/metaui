package com.metaui.fxbase.view.form;

import com.metaui.fxbase.model.FormFieldModel;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;

/**
 * MetaUI 文本输入域
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTextArea extends BaseFormField {
    private TextArea textArea;

    public MUTextArea(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        textArea = new TextArea();
        textArea.prefHeightProperty().bind(this.heightProperty());
        textArea.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(textArea);

        // 双向绑定值
        textArea.promptTextProperty().bindBidirectional(model.placeholderProperty());
    }

    @Override
    protected void setValue(String value) {
        textArea.setText(value);
    }

    @Override
    protected StringProperty valueProperty() {
        return textArea.textProperty();
    }
}
