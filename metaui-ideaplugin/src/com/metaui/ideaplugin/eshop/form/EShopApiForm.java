package com.metaui.ideaplugin.eshop.form;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.treeStructure.Tree;
import com.metaui.eshop.api.EShopApiModel;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import com.metaui.swing.model.MUComboBoxModel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/17.
 */
public class EShopApiForm {
    private ComboBox apiSiteCB;
    private ComboBox accountCB;
    private JButton btnAddAccount;
    private JButton btnModifyAccount;
    private JTabbedPane tabbedPane;
    private JPanel rootPane;
    private Tree apiTree;

    private Project project;
    private EShopApiModel model;

    public JPanel getRootPane() {
        return rootPane;
    }

    public EShopApiForm(Project project) {
        this.project = project;
        model = new EShopApiModel();

        apiTree.setRootVisible(false);
        initData();
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        apiSiteCB.setModel(new MUComboBoxModel(model.getApiSites()));
        apiSiteCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ApiSiteName siteName = (ApiSiteName) e.getItem();
                    try {
                        initApiSite(siteName);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // 添加账号
        btnAddAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountDialog dialog = new AccountDialog(model, null);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        // 修改账号
        btnModifyAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountDialog dialog = new AccountDialog(model, (Account) accountCB.getSelectedItem());
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        // api选中
        apiTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode note = (DefaultMutableTreeNode) apiTree.getLastSelectedPathComponent();
                if (note != null) {
                    Object obj = note.getUserObject();
                    if (obj instanceof ApiInfo) {
                        ApiInfo info = (ApiInfo) obj;
                        initApi(info);
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initApiSite(ApiSiteName siteName) throws Exception {
        // 设置账号
        accountCB.setModel(new MUComboBoxModel(model.getAccount(siteName)));
        // 设置分类
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        for (Category category : model.getCategory(siteName)) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category);
            root.add(categoryNode);
            if (category != null && category.getApiInfos() != null) {
                for (ApiInfo apiInfo : category.getApiInfos()) {
                    categoryNode.add(new DefaultMutableTreeNode(apiInfo));
                }
            }
        }
        apiTree.setModel(new DefaultTreeModel(root));
    }

    private void initApi(ApiInfo info) {
        int idx = tabbedPane.indexOfTab(info.getName());
        if (idx == -1) {
            ApiTestForm testForm = new ApiTestForm(project, info, this);
            tabbedPane.addTab(info.getName(), testForm.getRootPane());
            idx = tabbedPane.getTabCount() - 1;
        }
        // 选中
        tabbedPane.setSelectedIndex(idx);
    }

    public Account getAccount() {
        return (Account) accountCB.getSelectedItem();
    }

    public ApiSiteName getApiSiteName() {
        return (ApiSiteName) apiSiteCB.getSelectedItem();
    }
}
