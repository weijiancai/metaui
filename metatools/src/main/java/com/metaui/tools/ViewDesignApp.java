package com.metaui.tools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ViewDesignApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(ViewDesignApp.class.getResource("/com/metaui/tools/ViewDesign.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void main(String[] args) {
        launch(ViewDesignApp.class);
    }
}
