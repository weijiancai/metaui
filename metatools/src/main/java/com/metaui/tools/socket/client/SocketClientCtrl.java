package com.metaui.tools.socket.client;

import com.metaui.core.setting.PreferenceSettings;
import com.metaui.core.util.UNumber;
import com.metaui.core.util.UString;
import com.metaui.tools.socket.transport.ServiceTransport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketClientCtrl implements Initializable {
    public ListView<ServerInfo> serverListView;
    public TextField serverNameField;
    public TextField ipField;
    public TextField portField;
    public Button btnAddServer;
    public TabPane tabPane;

    private PreferenceSettings settings = PreferenceSettings.getInstance();
    private ObservableList<ServerInfo> list = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ServerInfo> serverList = settings.getObject("serverList");
        if (serverList != null) {
            list.addAll(serverList);
        }
        System.out.println(list);

        serverListView.setItems(list);
        serverListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ServerInfo>() {
            @Override
            public void changed(ObservableValue<? extends ServerInfo> observable, ServerInfo oldValue, ServerInfo newValue) {
                Tab tab = new Tab();
                tab.setText(newValue.toString());
                URL url = getClass().getResource("/com/metaui/tools/socket/client/serverTabPane.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                try {
                    tab.setContent(loader.load());
                    ServerTabCtrl ctrl = loader.getController();
                    ctrl.setServerInfo(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tabPane.getTabs().add(tab);
            }
        });
    }

    public void addServer(ActionEvent event) {
        String serverName = serverNameField.getText();
        System.out.println(serverName);
        String ip = ipField.getText();
        int port = UNumber.toInt(portField.getText());
        if (UString.isNotEmpty(ip) && port > 0) {
            ServerInfo info = new ServerInfo(serverName, ip, port);

            list.add(info);
            settings.put("serverList", new ArrayList<>(list));
        }
    }
}
