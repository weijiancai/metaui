package com.metaui.tools.socket.server.javafx;

import com.metaui.core.event.MEventHandler;
import com.metaui.core.util.UNumber;
import com.metaui.tools.socket.server.BaseClientConnect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFx主界面控制器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxServerCtrl implements Initializable {
    public Label serverIpLabel;
    public TextField serverPort;
    public Button btnStartServer;
    public Button btnStopServer;
    public TextArea serverLogTA;
    public ListView<BaseClientConnect> clientList;
    public ListView<String> logsList;

    private FxServerService serverService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startServer(ActionEvent event) {
        try {
            if (serverService == null) {
                int port = UNumber.toInt(serverPort.getText(), 9999);
                serverService = new FxServerService(port);
                serverService.messageProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        logsList.getItems().add(newValue);
                    }
                });
                serverService.exceptionProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        System.out.println(newValue);
                    }
                });
                // 监听客户端连接
                serverService.getServer().setOnClientConnectChange(new MEventHandler<BaseClientConnect>() {
                    @Override
                    public void handle(BaseClientConnect event) {
                        clientList.setItems(FXCollections.observableArrayList(serverService.getServer().getConnectList()));
                    }
                });
            }

            serverService.start();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogTA.appendText(e.toString());
        }
    }

    public void stopServer(ActionEvent event) {
        try {
            serverService.cancel();
            serverService.reset();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogTA.appendText(e.toString());
        }
    }
}
