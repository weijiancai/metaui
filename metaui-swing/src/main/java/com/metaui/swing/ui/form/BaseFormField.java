package com.metaui.swing.ui.form;

import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.view.IFormField;
import com.metaui.core.view.config.FormFieldConfig;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/28.
 */
public abstract class BaseFormField extends JPanel implements IFormField {
    protected FormFieldConfig config;

    public BaseFormField() {
    }

    public BaseFormField(FormFieldConfig config) {
        this.config = config;
        init();
    }

    @Override
    public void initPrep() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public void init() {
        initPrep();

        // 布局
        layoutUI();

        initAfter();
    }

    @Override
    public void layoutUI() {
        this.setBorder(new LineBorder(Color.red));
        this.setLayout(new BorderLayout());

        Box box = Box.createHorizontalBox();
        box.setBorder(new LineBorder(Color.blue));
        // label
        JLabel label = new JLabel(config.getDisplayName());
        label.setPreferredSize(new Dimension(config.getFormConfig().getLabelWidth(), 20));
        box.add(label);
        // label gap
        box.add(Box.createHorizontalStrut(config.getFormConfig().getLabelGap()));
        // field
        JComponent field = createField();
        int width = config.getWidth() > 0 ? config.getWidth() : config.getFormConfig().getFieldWidth();
        int height = 20;
        if (DisplayStyle.TEXT_AREA.equals(config.getDisplayStyle())) {
            height = config.getHeight() > 0 ? config.getHeight() : 60;
        }
        field.setPreferredSize(new Dimension(width, height));
        box.add(field);
        System.out.println(config.getDisplayName() + " = " + this.getWidth());
        Box.createHorizontalGlue();
        // field gap
        box.add(Box.createHorizontalStrut(config.getFormConfig().getFieldGap()));

        this.add(box, BorderLayout.CENTER);
    }

    protected abstract JComponent createField();

    @Override
    public void setValue(String value) {

    }

    public static BaseFormField getInstance(FormFieldConfig config) {
        DisplayStyle displayStyle = config.getDisplayStyle();
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            return new MUTextArea(config);
        } else if (DisplayStyle.PASSWORD == displayStyle) {
            return new MUPasswordField(config);
        }/* else if (DisplayStyle.COMBO_BOX == displayStyle || DisplayStyle.BOOLEAN == displayStyle) {
            return new MUComboBox(config);
        } else if (DisplayStyle.FILE_CHOOSER == displayStyle) {
            return new MUFileChooser(config);
        } else if (DisplayStyle.DIRECTORY_CHOOSER == displayStyle) {
            return new MUDirectoryChooser(config);
        } else if (DisplayStyle.TEXT == displayStyle) {
            return new MUText(config);
        }
        *//* else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            node = new MuDataSource(field);
        }*//* else if (DisplayStyle.DATE == displayStyle) {
            if (config.getFormModel().getFormType() == FormType.QUERY) {
                return new MUDateRange(config);
            } else {
                return new MUDate(config);
            }
        }*/
        return new MUTextField(config);
    }
}
