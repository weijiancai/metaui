package com.metaui.tools;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * 工具UI主界面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaToolsApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new Label("Hello")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(MetaToolsApp.class);
    }
}
