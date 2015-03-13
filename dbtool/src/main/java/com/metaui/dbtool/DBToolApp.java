package com.metaui.dbtool;


import com.metaui.fxbase.facade.IFacade;
import com.metaui.fxbase.BaseApp;

/**
 * 数据库工具App
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBToolApp extends BaseApp {

    @Override
    public IFacade getFacade() {
        return DBToolFacade.getInstance();
    }

    public static void main(String[] args) {
        launch(DBToolApp.class);
    }
}
