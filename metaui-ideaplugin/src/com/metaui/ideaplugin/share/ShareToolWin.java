package com.metaui.ideaplugin.share;

import com.intellij.openapi.wm.impl.IdeGlassPaneImpl;
import com.intellij.ui.treeStructure.Tree;
import com.metaui.core.view.config.FormConfig;
import com.metaui.core.view.config.FormFieldConfig;
import com.metaui.swing.ui.MuForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/26.
 */
public class ShareToolWin {
    private JPanel root;
    private Tree bookTree;
    private JButton btnAddBook;

    public ShareToolWin() {
        initData();
        btnAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                frame.setLocationRelativeTo(null);
                frame.setTitle("网店API");
                int width = 1280;
                int height = 800;
                frame.setSize(width, height);
                // 居中
                int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
                int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
                frame.setLocation(w, h);

                Container contentPane = frame.getContentPane();
                contentPane.setLayout(new BorderLayout());
                FormConfig config = new FormConfig();
                java.util.List<FormFieldConfig> list = new ArrayList<>();
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
                field.setDisplayName("是否已经发货");
                field.setFormConfig(config);
                list.add(field);

                field = new FormFieldConfig();
                field.setDisplayName("是否已经收到订单");
                field.setFormConfig(config);
                list.add(field);

                MuForm form = new MuForm(config);
                form.init();
                contentPane.add(form, BorderLayout.CENTER);

                IdeGlassPaneImpl ideGlassPane = new IdeGlassPaneImpl(frame.getRootPane());
                frame.getRootPane().setGlassPane(ideGlassPane);

                frame.setVisible(true);
            }
        });
    }

    private void initData() {
        ShareManager manager = new ShareManager();

        try {
            bookTree.setModel(manager.getBookTreeModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JPanel getRootPanel() {
        return root;
    }
}
