package com.metaui.fxbase.win.db;

import com.metaui.core.ui.IView;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.view.desktop.MUTabsDesktop;

/**
 * 数据库桌面
 *
 * @author wei_jc
 * @since 1.0
 */
public class DBDesktop extends MUTabsDesktop implements IView {
    private DBNavView navView;

    public DBDesktop(AppModel app) {
        super(app);
    }

    @Override
    public void createLeft() {
        navView = new DBNavView();
        this.setLeft(navView);
        navView.initUI();
    }

    @Override
    public void initUI() {
        super.initUI();
    }
}
