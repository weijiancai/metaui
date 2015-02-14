package com.metaui.fxbase.ui.component.form;

import com.metaui.core.ui.layout.property.FormFieldProperty;
import com.metaui.fxbase.ui.IValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * MetaUI 数字输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuNumberField extends BaseFormField implements IValue {
    private TextField textField;

    public MuNumberField(FormFieldProperty fieldConfig) {
        super(fieldConfig);
        init();
    }

    @Override
    protected void initPrep() {
        textField = new TextField();
        textField.prefWidthProperty().bind(this.widthProperty());
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{textField};
    }

    @Override
    public String value() {
        return textField.getText();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        textField.setText(value);
    }
}
