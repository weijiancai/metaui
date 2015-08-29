package com.metaui.fxbase;

import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.view.desktop.MUTabsDesktop;
import com.metaui.fxbase.view.tree.MUTree;
import com.metaui.fxbase.win.db.DBDesktop;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DBApp extends BaseApplication {
    private DBDesktop desktop;

    public DBApp() {
        AppModel model = new AppModel();
        model.setTitle("DB Browser");
        setAppModel(model);

        desktop = new DBDesktop(model);
    }

    @Override
    protected void onStart() {
        MUTree navTree = desktop.getNavTree();
    }

    @Override
    public MUTabsDesktop getDesktop() {
        return desktop;
    }

    public static void main(String[] args) {
        launch(DBApp.class);
    }
}
