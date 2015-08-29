package com.metaui.fxbase.win.db;

import com.metaui.core.ui.IView;
import javafx.scene.layout.BorderPane;

/**
 * 数据库浏览器
 *
 * @author wei_jc
 * @since 1.0
 */
public class DBBrowserWin extends BorderPane implements IView {
    private DBNavView navView;

    public DBBrowserWin() {
        initUI();
    }

    @Override
    public void initUI() {
        navView = new DBNavView();
        this.setLeft(navView);
    }
}
