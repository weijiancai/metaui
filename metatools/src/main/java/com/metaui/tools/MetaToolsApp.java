package com.metaui.tools;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;

/**
 * 工具UI主界面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaToolsApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/com/metaui/tools/socket/server/ServerSocket.fxml");
        Parent parent = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(MetaToolsApp.class);
    }
}
