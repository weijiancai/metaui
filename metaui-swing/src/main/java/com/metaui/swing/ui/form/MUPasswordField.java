package com.metaui.swing.ui.form;


import com.metaui.core.view.config.FormFieldConfig;

import javax.swing.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUPasswordField extends BaseFormField {
    private JPasswordField passwordField;

    public MUPasswordField(FormFieldConfig config) {
        super(config);
    }

    @Override
    protected JComponent createField() {
        passwordField = new JPasswordField();
        return passwordField;
    }

    @Override
    public void setValue(String value) {
        passwordField.setText(value);
    }
}
