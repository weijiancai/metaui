package com.metaui.fxbase;

import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.JdbcDrivers;

/**
 * @author wei_jc
 * @since 1.0
 */
public class EctongsDBApp extends DBApp {

    private static void initDataSource() throws Exception {
        DBDataSource ds = new DBDataSource();
        ds.setId("java_dev");
        ds.setName("java_dev");
        ds.setDisplayName("开发服务器");
        ds.setDatabaseType(DatabaseType.SQLSERVER);
        ds.setDriverClass(JdbcDrivers.SQL_SERVER);
        ds.setUrl("jdbc:sqlserver://192.168.56.1:1433;databaseName=test_wy");
        ds.setUserName("sa");
        ds.setPwd("yhdtpass2013#");

        DataSourceManager.addDataSource(ds);

        ds = new DBDataSource();
        ds.setId("ecargo");
        ds.setName("ecargo");
        ds.setDisplayName("ecargo");
        ds.setDatabaseType(DatabaseType.ORACLE);
        ds.setDriverClass(JdbcDrivers.ORACLE);
        ds.setUrl("jdbc:oracle:thin:@//192.168.31.118:1521/orcl");
        ds.setUserName("ecargo");
        ds.setPwd("ecargo");

        DataSourceManager.addDataSource(ds);
    }

    public static void main(String[] args) {
        try {
            initDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(EctongsDBApp.class);
    }
}
