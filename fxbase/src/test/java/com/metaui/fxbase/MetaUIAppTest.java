package com.metaui.fxbase;

import com.metaui.fxbase.desktop.MUListViewTabsDesktop;
import com.metaui.fxbase.vm.AppVM;
import com.metaui.fxbase.vm.NavMenuVM;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaUIAppTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppVM viewModel = new AppVM();
        primaryStage.titleProperty().bind(viewModel.titleProperty());

        // 导航菜单
        NavMenuVM menu1 = new NavMenuVM();
        menu1.setTitle("apk工具");
        viewModel.getNavMenuList().add(menu1);

        MUListViewTabsDesktop desktop = new MUListViewTabsDesktop(viewModel);
        primaryStage.setScene(new Scene(desktop));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(MetaUIAppTest.class);
    }
}
