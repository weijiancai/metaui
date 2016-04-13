package com.metaui.eshop.ui;

import com.metaui.core.util.UString;
import com.metaui.eshop.api.ApiFactory;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.ApiTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.api.domain.ParamInfo;
import com.metaui.fxbase.dialog.MUDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class EShopApiCtrl implements Initializable {
    @FXML
    private ComboBox<ApiSiteName> apiSiteCB;
    @FXML
    private ChoiceBox<Account> accountCB;
    @FXML
    private Button btnAddAccount;
    @FXML
    private Button btnModifyAccount;
    @FXML
    private Accordion categoryAccordion;
    @FXML
    private Label titleLabel;
    @FXML
    private Label apiDescLabel;
    @FXML
    private Label explainLabel;
    @FXML
    private Label authorizeLabel;
    @FXML
    private Label sceneLabel;
    @FXML
    private TableView<ParamInfo> sysParamTable;
    @FXML
    private TableView<ParamInfo> appParamTable;
    @FXML
    private TableView<ParamInfo> returnParamTable;
    @FXML
    private WebView apiWebView;
    @FXML
    private GridPane appParamGridPane;
    @FXML
    private Button btnTest;
    @FXML
    private TextArea taResult;

    private List<Node> nodes;
    private ApiInfo apiInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // aip接口站点
        apiSiteCB.setItems(FXCollections.observableArrayList(ApiSiteName.getAll()));
        apiSiteCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ApiSiteName>() {
            @Override
            public void changed(ObservableValue<? extends ApiSiteName> observable, ApiSiteName oldValue, ApiSiteName newValue) {
                initSite(newValue);
            }
        });

        // 添加账号按钮
        btnAddAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = getClass().getResource("/com.metaui.eshop.ui/accountDialog.fxml");
                    Parent parent = FXMLLoader.load(url);
                    MUDialog.showCustomDialog("添加账号", parent, null);
                } catch (Exception e) {
                    MUDialog.showException(e);
                }
            }
        });
        // 修改账号按钮
        btnModifyAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    URL url = getClass().getResource("/com.metaui.eshop.ui/accountDialog.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent parent = loader.load();
                    AccountDialogCtrl dialog = loader.getController();
                    dialog.setAccount(accountCB.getValue());
                    MUDialog.showCustomDialog("添加账号", parent, null);
                } catch (Exception e) {
                    MUDialog.showException(e);
                }
            }
        });
        // Api接口测试
        btnTest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (apiInfo == null) {
                        return;
                    }
                    Account account = accountCB.getValue();
                    if (account == null) {
                        MUDialog.showMessage("请选择账号！");
                        return;
                    }
                    taResult.setText("");
                    ApiTester tester = ApiFactory.getTester(apiSiteCB.getValue());
                    String result = tester.test(account, apiInfo, getParams());
                    taResult.setText(result);
                } catch (Exception e) {
                    MUDialog.showException(e);
                }
            }
        });

        // 初始化参数table列信息
        initParamTableColumns();
    }

    private void initParamTableColumns() {
        TableColumn idCol = new TableColumn();
        idCol.setText("ID");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn nameCol = new TableColumn();
        nameCol.setText("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn typeCol = new TableColumn();
        typeCol.setText("参数类型");
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));

        TableColumn requireCol = new TableColumn();
        requireCol.setText("必须");
        requireCol.setCellValueFactory(new PropertyValueFactory("isRequire"));

        TableColumn descCol = new TableColumn();
        descCol.setText("描述");
        descCol.setCellValueFactory(new PropertyValueFactory("desc"));

        TableColumn exampleCol = new TableColumn();
        exampleCol.setText("示例");
        exampleCol.setCellValueFactory(new PropertyValueFactory("example"));

        sysParamTable.getColumns().addAll(idCol, nameCol, typeCol, requireCol, descCol, exampleCol);
        appParamTable.getColumns().addAll(idCol, nameCol, typeCol, requireCol, descCol, exampleCol);
        returnParamTable.getColumns().addAll(idCol, nameCol, typeCol, requireCol, descCol, exampleCol);
    }

    private void initSite(ApiSiteName siteName) {
        try {
            // 初始化Accordion
            initAccordion(siteName);
            // 账号
            accountCB.setItems(FXCollections.observableArrayList(ApiFactory.getAccounts(siteName)));
        } catch (Exception e) {
            MUDialog.showException(e);
        }
    }

    private void initAccordion(ApiSiteName siteName) throws Exception {
        // 移除所有Accordion Pane
        categoryAccordion.getPanes().removeAll(categoryAccordion.getPanes());

        List<Category> categories = ApiFactory.getApi(siteName);
        for (Category category : categories) {
            List<ApiInfo> list = category.getApiInfos();
            ListView<ApiInfo> listView = new ListView<>();
            if (list != null) {
                listView.setItems(FXCollections.observableArrayList(list));
                // 添加选中list事件
                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ApiInfo>() {
                    @Override
                    public void changed(ObservableValue<? extends ApiInfo> observable, ApiInfo oldValue, ApiInfo newValue) {
                        initApiInfo(newValue);
                    }
                });
            }

            TitledPane pane = new TitledPane(category.getName(), listView);
            categoryAccordion.getPanes().add(pane);
        }
    }

    private void initApiInfo(ApiInfo info) {
        this.apiInfo = info;
        // 清空返回结果
        taResult.setText("");

        titleLabel.setText(info.getId() + "    " + info.getName());
        apiDescLabel.setText(info.getDesc());
        explainLabel.setText(info.getExplain());
        authorizeLabel.setText(info.getAuthorize());
        sceneLabel.setText(info.getScene());
        // 系统参数
        List<ParamInfo> sysParams = info.getSysParams();
        if (sysParams != null) {
            sysParamTable.setItems(FXCollections.observableArrayList(info.getSysParams()));
        }
        apiWebView.getEngine().load(info.getUrl());
        // 初始化请求参数GridPane
        initGridPane(info);
    }

    private void initGridPane(ApiInfo info) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        if (nodes.size() > 0) {
            // 删除以前节点
            for (Node node : nodes) {
                appParamGridPane.getChildren().remove(node);
            }
        }
        // 增加节点
        int row = 1;
        for (ParamInfo param : info.getAppParams()) {
            Label idLabel = new Label(param.getId());
            if (param.isRequire()) {
                idLabel.setTextFill(Color.RED);
            }
            nodes.add(idLabel);
            TextField textField = new TextField();
            textField.setPromptText(param.getExample());
            textField.setUserData(param);
            nodes.add(textField);
            /*Label descLabel = new Label(param.getDesc());
            nodes.add(descLabel);*/

            appParamGridPane.add(idLabel, 0, row);
            appParamGridPane.add(textField, 1, row);
//            appParamGridPane.add(descLabel, 2, row);
            row++;
//            out_sid=921889466257

        }
    }

    private Map<String,String> getParams() {
        Map<String, String> params = new HashMap<>();
        for (Node node : nodes) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                ParamInfo param = (ParamInfo) tf.getUserData();
                String value = tf.getText();
                if (UString.isNotEmpty(value)) {
                    params.put(param.getId(), value);
                }
            }
        }
        return params;
    }
}
