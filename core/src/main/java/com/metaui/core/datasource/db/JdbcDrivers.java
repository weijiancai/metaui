package com.metaui.core.datasource.db;

/**
 * Jdbc驱动类名接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface JdbcDrivers {
    String SQL_SERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String MYSQL = "com.mysql.jdbc.Driver";
    String HSQLDB = "org.hsqldb.jdbcDriver";
    String ORACLE = "oracle.jdbc.driver.OracleDriver";
}
