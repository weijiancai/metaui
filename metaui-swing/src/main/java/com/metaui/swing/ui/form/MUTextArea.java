package com.metaui.swing.ui.form;

import com.metaui.core.view.config.FormFieldConfig;

import javax.swing.*;

/**
 * MetaUI 文本输入域
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTextArea extends BaseFormField {
    private JTextArea textArea;

    public MUTextArea(FormFieldConfig config) {
        super(config);
    }

    @Override
    protected JComponent createField() {
        textArea = new JTextArea();
        return textArea;
    }

    @Override
    public void setValue(String value) {
        textArea.setText(value);
    }
}
