package com.metaui.fxbase.view.form;

import com.metaui.fxbase.model.FormFieldModel;
import javafx.scene.control.TextField;

/**
 *  MetaUI 文本输入框
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTextField extends BaseFormField {
    private TextField textField;

    public MUTextField(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        textField = new TextField();
        textField.prefWidthProperty().bind(this.prefWidthProperty());
        textField.prefHeightProperty().bind(this.prefHeightProperty());
        this.getChildren().add(textField);

        // 双向绑定值
        textField.textProperty().bindBidirectional(model.valueProperty());
        textField.promptTextProperty().bindBidirectional(model.placeholderProperty());
    }

    @Override
    protected void setValue(String value) {
        textField.setText(value);
    }
}
