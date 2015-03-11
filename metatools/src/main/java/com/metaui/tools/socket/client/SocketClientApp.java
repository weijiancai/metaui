package com.metaui.tools.socket.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * 客户端主界面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SocketClientApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/com/metaui/tools/socket/client/clientSocket.fxml");
        Parent parent = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("客户端");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(SocketClientApp.class);
    }
}
