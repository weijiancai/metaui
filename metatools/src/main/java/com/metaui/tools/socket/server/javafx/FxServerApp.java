package com.metaui.tools.socket.server.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * JavaFx服务器端主界面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxServerApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/com/metaui/tools/socket/server/javafx/ServerSocket.fxml");
        Parent parent = FXMLLoader.load(url);
        stage.setScene(new Scene(parent));
        stage.setTitle("服务器端");
        stage.show();
    }

    public static void main(String[] args) {
        launch(FxServerApp.class);
    }
}
