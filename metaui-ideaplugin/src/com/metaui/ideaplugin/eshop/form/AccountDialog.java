package com.metaui.ideaplugin.eshop.form;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.EShopApiModel;
import com.metaui.eshop.api.domain.Account;
import com.metaui.swing.model.MUComboBoxModel;

import javax.swing.*;
import java.awt.event.*;

public class AccountDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private ComboBox cbApiSiteNames;
    private JCheckBox cbSandbox;
    private JTextField tfName;
    private JTextField tfToken;
    private JTextField tfKey;
    private JTextField tfSecret;

    private Account account;
    private EShopApiModel model;

    @SuppressWarnings("unchecked")
    public AccountDialog(EShopApiModel model, Account account) {
        this.model = model;
        this.account = account;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        if (account != null) {
            cbApiSiteNames.setSelectedItem(account.getApiSite());
            cbSandbox.setSelected(account.isSandbox());
            tfName.setText(account.getName());
            tfToken.setText(account.getToken());
            tfKey.setText(account.getKey());
            tfSecret.setText(account.getSecret());
        }

        cbApiSiteNames.setModel(new MUComboBoxModel(model.getApiSites()));
    }

    private void onOK() {
// add your code here]
        Account account = new Account();
        account.setApiSite((ApiSiteName) cbApiSiteNames.getSelectedItem());
        account.setName(tfName.getText());
        account.setSecret(tfSecret.getText());
        account.setKey(tfKey.getText());
        account.setToken(tfToken.getText());
        account.setSandbox(cbSandbox.isSelected());

        try {
            model.saveAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            Messages.showMessageDialog("保存账户失败！" + e.getMessage(), "消息", Messages.getInformationIcon());
        }

        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AccountDialog dialog = new AccountDialog(null, null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
