package com.metaui.eshop;

import com.metaui.core.moudle.ModuleManager;
import com.metaui.core.sys.SystemManager;
import com.metaui.eshop.moudle.EShopModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * 网店应用程序入口
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class EShopApp  extends Application {
    public static void main(String[] args) throws Exception {
        // 注册模块
        ModuleManager.getInstance().regist(new EShopModule());
        // 系统初始化
        SystemManager.getInstance().init();
        // 启动
        launch(EShopApp.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/com.metaui.eshop.ui/EShopApiUI.fxml");
        Parent parent = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
