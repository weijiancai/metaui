package com.metaui.swing.ui.form;


import com.metaui.core.view.config.FormFieldConfig;

import javax.swing.*;

/**
 *  MetaUI 文本输入框
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTextField extends BaseFormField {
    private JTextField textField;

    public MUTextField(FormFieldConfig config) {
        super(config);
    }

    @Override
    protected JComponent createField() {
        textField = new JTextField();

        return textField;
    }

    @Override
    public void setValue(String value) {
        textField.setText(value);
    }
}
