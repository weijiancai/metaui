package com.metaui.fxbase.view.form;

import com.metaui.fxbase.model.FormFieldModel;
import javafx.beans.property.StringProperty;
import javafx.scene.control.PasswordField;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUPasswordField extends BaseFormField {
    private PasswordField passwordField;

    public MUPasswordField(FormFieldModel model) {
        super(model);
    }

    @Override
    protected void initPrep() {
        passwordField = new PasswordField();
        passwordField.prefWidthProperty().bind(this.prefWidthProperty());
        passwordField.prefHeightProperty().bind(this.prefHeightProperty());
        this.getChildren().add(passwordField);

        // 双向绑定值
        passwordField.promptTextProperty().bindBidirectional(model.placeholderProperty());
    }

    @Override
    protected void setValue(String value) {
        passwordField.setText(value);
    }

    @Override
    protected StringProperty valueProperty() {
        return passwordField.textProperty();
    }
}
