package com.metaui.tools.socket.server;

import com.metaui.core.util.UNumber;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Socket Server控制器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketServerCtrl implements Initializable {
    public Label serverIpLabel;
    public TextField serverPort;
    public Button btnStartServer;
    public Button btnStopServer;
    public TextArea serverLogTA;
    public ListView<ClientConnect> clientList;
    public ListView<String> logsList;

    private SocketServer server;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location);
    }

    public void startServer(ActionEvent event) {
        try {
            if (server == null) {
                int port = UNumber.toInt(serverPort.getText(), 9999);
                server = new SocketServer(port);
            }
            logsList.setItems(server.getLogs());

            server.start();
            clientList.setItems(server.getConnectList());
        } catch (Exception e) {
            e.printStackTrace();
            serverLogTA.appendText(e.toString());
        }
    }

    public void stopServer(ActionEvent event) {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
            serverLogTA.appendText(e.toString());
        }
    }
}
