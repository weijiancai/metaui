package com.metaui.eshop.ui;

import com.metaui.eshop.api.ApiFactory;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.domain.Account;
import com.metaui.fxbase.dialog.MUDialog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/10.
 */
public class AccountDialogCtrl implements Initializable {
    @FXML
    private ComboBox<ApiSiteName> cbApiSite;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfToken;
    @FXML
    private TextField tfKey;
    @FXML
    private TextField tfSecret;
    @FXML
    private Button btnSave;
    @FXML
    private CheckBox isSandbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // aip接口站点
        cbApiSite.setItems(FXCollections.observableArrayList(ApiSiteName.getAll()));
        // 保存
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account account = new Account();
                account.setApiSite(cbApiSite.getValue());
                account.setName(tfName.getText());
                account.setToken(tfToken.getText());
                account.setKey(tfKey.getText());
                account.setSecret(tfSecret.getText());
                account.setSandbox(isSandbox.isSelected());
                try {
                    ApiFactory.addAccount(account);
                } catch (Exception e) {
                    MUDialog.showException(e);
                }
            }
        });
    }

    public void setAccount(Account account) {
        if (account != null) {
            cbApiSite.setValue(account.getApiSite());
            tfName.setText(account.getName());
            tfToken.setText(account.getToken());
            tfKey.setText(account.getKey());
            tfSecret.setText(account.getSecret());
            isSandbox.setSelected(account.isSandbox());
        }
    }
}
