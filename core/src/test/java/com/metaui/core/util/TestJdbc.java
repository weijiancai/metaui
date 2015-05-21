package com.metaui.core.util;

import com.metaui.core.datasource.db.JdbcDrivers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TestJdbc {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName(JdbcDrivers.SQL_SERVER);
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://192.168.56.1:1433;databaseName=test_wy", "sa", "yhdtpass2013#");
        System.out.println(connection);
        connection.close();
    }
}
