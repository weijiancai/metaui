package com.metaui.fxbase;

import com.metaui.core.config.PathManager;
import com.metaui.core.config.ProjectConfig;
import com.metaui.core.config.SystemManager;
import com.metaui.core.ui.IView;
import com.metaui.core.ui.config.ConfigInit;
import com.metaui.core.util.HSqlDBServer;
import com.metaui.core.util.UIO;
import com.metaui.fxbase.facade.IFacade;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.ui.FxDesktop;
import com.metaui.fxbase.ui.IDesktop;
import com.metaui.fxbase.ui.view.FxView;
import com.metaui.fxbase.ui.view.MUDialog;
import com.metaui.fxbase.view.desktop.MUTabsDesktop;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;
import org.apache.log4j.PropertyConfigurator;

/**
 * JavaFX 主应用程序
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseApplication extends Application {
    /** 是否APPLET运行方式 */
    public static boolean IS_APPLET;
    private static BaseApplication instance;
    private AppModel model;

    protected Scene scene;
    private Stage stage;
    protected MUTabsDesktop desktop;

    public BaseApplication() {
        // 设置日志目录属性
        System.setProperty("logs_dir", PathManager.getLogPath().getAbsolutePath());
        try {
            PropertyConfigurator.configure(UIO.getInputStream("/log4j.properties", UIO.FROM.CP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        instance = this;
        try {
            JSObject browser = getHostServices().getWebContext();
            IS_APPLET =  browser != null;
        } catch (Exception e) {
            IS_APPLET = false;
        }

    }

    public void setAppModel(AppModel model) {
        this.model = model;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.titleProperty().bind(model.titleProperty());

        // 全屏显示
        Rectangle2D primaryScreenBonds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBonds.getMinX());
        stage.setY(primaryScreenBonds.getMinY());
        stage.setWidth(primaryScreenBonds.getWidth());
        stage.setHeight(primaryScreenBonds.getHeight());

        desktop = getDesktop();
        scene = new Scene(desktop);

        setSkin(R.skin.DEFAULT);
        // show stage
        stage.setScene(scene);

        // 调用onStart
        onStart();
        // 初始化桌面
        desktop.initUI();

        stage.show();
    }

    protected abstract void onStart();

    public MUTabsDesktop getDesktop() {
        if (desktop == null) {
            desktop = new MUTabsDesktop(model);
        }
        return desktop;
    }

    public void setSkin(String skin) {
        scene.getStylesheets().addAll(R.class.getResource("/fxbase/skin/" + skin + "/" + skin + ".css").toExternalForm());
    }

    @Override
    public void stop() throws Exception {
        HSqlDBServer.getInstance().stop();
        super.stop();
    }

    /**
     * 获取BaseApp实例
     *
     * @return 返回BaseApp实例
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 获取JavaFx主Stage
     *
     * @return 返回JavaFx主Stage
     */
    public Stage getStage() {
        return stage;
    }
}