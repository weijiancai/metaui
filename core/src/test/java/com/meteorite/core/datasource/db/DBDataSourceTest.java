package com.meteorite.core.datasource.db;

import com.metaui.core.datasource.DataSource;
import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.JdbcDrivers;
import com.metaui.core.datasource.request.ExpDbDdlRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBDataSourceTest {

    @Test
    public void testFindResourceByPath() throws Exception {
        String path = "/table/sys_db_version";
        DataSource dataSource = DataSourceManager.getSysDataSource();
        dataSource.findResourceByPath(path);
    }

    @Test
    public void testExpDdl() throws Exception {
        ExpDbDdlRequest request = new ExpDbDdlRequest();
        request.setExpDbType(DatabaseType.HSQLDB);
//        request.setSaveFilePath("D:\\workspace\\metaui\\core\\src\\main\\resources\\db_upgrade\\1.0.0\\metaui_hsqldb.sql");
//        DBDataSource dataSource = new DBDataSource("metaui", JdbcDrivers.MYSQL, "jdbc:mysql://localhost:3306/metaui", "root", "root", "1.0");
        request.setSaveFilePath("D:\\workspace\\my\\metaui\\core\\src\\main\\resources\\db_upgrade\\1.0.0\\metaui_hsqldb.sql");
        DBDataSource dataSource = new DBDataSource("metaui", JdbcDrivers.MYSQL, "jdbc:mysql://localhost:3906/metaui", "root", "wjc+7758521", "1.0");
//        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        dataSource.expDdl(request);
    }
}