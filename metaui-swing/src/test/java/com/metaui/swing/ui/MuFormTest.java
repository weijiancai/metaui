package com.metaui.swing.ui;

import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.view.config.FormConfig;
import com.metaui.core.view.config.FormFieldConfig;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/28.
 */
public class MuFormTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("测试Form");
        frame.setSize(1000, 600);
        FormConfig config = new FormConfig();
        List<FormFieldConfig> list = new ArrayList<>();
        config.setFields(list);

        FormFieldConfig field = new FormFieldConfig();
        field.setDisplayName("书号");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("书名");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("作者");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("定价");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("出版社");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("图书分类");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("中图分类法");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("密码");
        field.setFormConfig(config);
        field.setDisplayStyle(DisplayStyle.PASSWORD);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("是否已经发货");
        field.setFormConfig(config);
        field.setSingleLine(true);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("是否已经收到订单");
        field.setFormConfig(config);
        list.add(field);

        field = new FormFieldConfig();
        field.setDisplayName("备注");
        field.setFormConfig(config);
        field.setDisplayStyle(DisplayStyle.TEXT_AREA);
        field.setSingleLine(true);
        list.add(field);

        MuForm form = new MuForm(config);
        form.init();
        frame.setLayout(new BorderLayout());
        frame.add(form, BorderLayout.NORTH);
//        frame.setContentPane(form);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}