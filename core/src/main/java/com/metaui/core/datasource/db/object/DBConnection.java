package com.metaui.core.datasource.db.object;

import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.eventdata.SqlExecuteEventData;
import com.metaui.core.observer.Subject;

import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * 数据库连接
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface DBConnection {
    Connection getConnection() throws Exception;

    /**
     * 获得结果集
     *
     * @param sql sql语句
     * @return 返回查询结果集
     */
    List<DataMap> getResultSet(String sql);

    /**
     * 获得结果集
     *
     * @param sql sql语句
     * @param keyToLower DataMap的key值，是否转换为小写
     * @return 返回查询结果集
     */
    List<DataMap> getResultSet(String sql, boolean keyToLower);

    /**
     * 获得数据库加载器
     *
     * @return 返回数据库加载器
     */
    DBLoader getLoader() throws Exception;

    /**
     * 获得数据库类型
     *
     * @return 返回数据库类型
     */
    DatabaseType getDatabaseType() throws Exception;

    /**
     * 获得当前连接的Schema
     *
     * @return 返回当前连接的Schema
     * @throws Exception
     */
    DBSchema getSchema() throws Exception;

    /**
     * 获得当前数据库的所有schema
     *
     * @return 返回当前数据库的所有schema
     * @throws Exception
     */
    List<DBSchema> getSchemas() throws Exception;

    /**
     * 执行SQL脚本文件
     *
     * @param sqlFile SQL脚本文件
     * @throws Exception
     */
    void execSqlFile(File sqlFile) throws Exception;

    /**
     * 执行SQL脚本
     *
     * @param script 脚本
     * @param splitStr 分隔符，默认为;
     * @throws Exception
     */
    void execSqlScript(String script, String splitStr) throws Exception;

    /**
     * 获得数据源
     *
     * @return 返回数据源
     * @since 1.0.0
     */
    DBDataSource getDataSource();

    /**
     * 是否可用
     *
     * @return 如果可以获得数据库连接，返回true，否则返回false
     */
    boolean isAvailable();

    /**
     * 获得执行Sql主题
     *
     * @return 返回执行Sql主题
     */
    Subject<SqlExecuteEventData> getSqlExecuteSubject();

    /**
     * 设置当前Schema
     *
     * @param currentSchema 当前Schema
     */
    void setCurrentSchema(DBSchema currentSchema);
}
