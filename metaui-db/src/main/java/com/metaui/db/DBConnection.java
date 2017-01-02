package com.metaui.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接
 *
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/2.
 */
public class DBConnection {
    private String url;
    private String userName;
    private String password;
    private String schema;

    public DBConnection(String url, String userName, String password) throws ClassNotFoundException, SQLException {
        this.url = url;
        this.userName = userName;
        this.password = password;
        // 初始化连接
        if (url.startsWith("jdbc:sqlserver:")) { // SqlServer
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            schema = connection.getCatalog();
            connection.close();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSchema() {
        return schema;
    }
}
