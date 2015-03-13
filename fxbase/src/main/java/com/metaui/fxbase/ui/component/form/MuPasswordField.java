package com.metaui.fxbase.ui.component.form;

import com.metaui.core.ui.layout.property.FormFieldProperty;
import com.metaui.core.ui.IValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;

/**
 * MetaUI 密码输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuPasswordField extends BaseFormField implements IValue {
    private PasswordField passwordField;

    public MuPasswordField(FormFieldProperty property) {
        super(property);
        init();
    }

    @Override
    protected void initPrep() {
        passwordField = new PasswordField();
        passwordField.setPrefWidth(config.getWidth());
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                valueProperty().set(newValue);
            }
        });
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{passwordField};
    }

    @Override
    public String value() {
        return passwordField.getText();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        passwordField.setText(value);
    }

}
