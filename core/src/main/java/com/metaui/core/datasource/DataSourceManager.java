package com.metaui.core.datasource;

import com.metaui.core.config.SystemConfig;
import com.metaui.core.datasource.classpath.ClassPathDataSource;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.datasource.db.object.impl.DBObjectImpl;
import com.metaui.core.datasource.request.BaseRequest;
import com.metaui.core.datasource.request.IResponse;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.util.UFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.metaui.core.config.SystemConfig.DIR_SYSTEM_HSQL_DB;
import static com.metaui.core.config.SystemConfig.SYSTEM_NAME;

/**
 * 数据源管理
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DataSourceManager {
    private static final Logger log = Logger.getLogger(DataSourceManager.class);

    private static List<DataSource> dataSources = new ArrayList<DataSource>();
    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

    /**
     * 添加数据源
     *
     * @param dataSource 数据源
     */
    public static void addDataSource(DataSource dataSource) throws Exception {
        dataSources.add(dataSource);
        dataSourceMap.put(dataSource.getId(), dataSource);
    }

    /**
     * 获得数据源
     *
     * @param dataSourceId 数据源ID
     * @return 返回数据源
     */
    public static DataSource getDataSource(String dataSourceId) {
        return dataSourceMap.get(dataSourceId);
    }

    /**
     * 根据数据源名称，获得数据源信息
     *
     * @param dataSourceName 数据源名称
     * @return 返回数据源
     */
    public static DataSource getDataSourceByName(String dataSourceName) {
        for (DataSource dataSource : dataSources) {
            if (dataSource.getName().equals(dataSourceName)) {
                return dataSource;
            }
        }

        return null;
    }


    /**
     * 获得所有数据源
     *
     * @since 1.0.0
     * @return 返回所有数据库数据源
     */
    public static List<DataSource> getDataSources() {
        return dataSources;
    }

    /**
     * 获得系统数据源
     *
     * @return 返回系统数据源
     */
    public static DBDataSource getSysDataSource() {
        DBDataSource dataSource = (DBDataSource) dataSourceMap.get("MetaUI_DataSource");
        if (dataSource == null) {
           /* File sysDbFile = null;
            try {
                sysDbFile = UFile.createFile(DIR_SYSTEM_HSQL_DB, SYS_DB_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataSource = new DBDataSource(SystemConfig.SYS_DB_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/sys", "sa", "", SystemConfig.SYS_DB_VERSION);
            dataSource.setDatabaseType(DatabaseType.HSQLDB);
            assert sysDbFile != null;
            dataSource.setFilePath(sysDbFile.getAbsolutePath());*/
            return initSysDataSource();
        }
        return dataSource;
    }

    /**
     * 从类路径下/db.property文件中初始化系统数据源
     *
     * @return 返回系统数据源
     */
    private static DBDataSource initSysDataSource() {
        DBDataSource dataSource;
        // 从类路径下获得db.property配置信息
        ResourceItem dbProperty = ClassPathDataSource.getInstance().getResource("db.properties");
        if (dbProperty == null) { // 创建hsqldb进程数据库
            File sysDbFile;
            try {
                sysDbFile = UFile.createFile(DIR_SYSTEM_HSQL_DB, SYSTEM_NAME);
                log.info("初始化系统数据源：" + sysDbFile.getAbsolutePath());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("创建系统数据源文件失败！");
            }
            dataSource = new DBDataSource(SYSTEM_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:file://" + sysDbFile.getAbsolutePath(), "sa", "", SystemConfig.SYS_DB_VERSION);
            dataSource.setDatabaseType(DatabaseType.HSQLDB);
        } else {
            Properties properties = new Properties();
            try {
                log.info("db.properties URI = " + dbProperty.getURI());

                properties.load(dbProperty.getInputStream());
                String driverClass = properties.getProperty("driverClass");
                String url = properties.getProperty("url");
                String userName = properties.getProperty("userName");
                String password = properties.getProperty("password");
                log.info("初始化系统数据源：" + url);
                dataSource = new DBDataSource(SYSTEM_NAME, driverClass, url, userName, password, SystemConfig.SYS_DB_VERSION);
            } catch (Exception e) {
                log.error("获得db.property文件失败！", e);
                throw new RuntimeException("获得db.property文件失败！");
            }
        }


        dataSource.setId("MetaUI_DataSource");
        dataSource.setDisplayName("系统数据源");
        dataSource.setType(DataSourceType.DATABASE);
        dataSources.add(dataSource);
        dataSourceMap.put(dataSource.getId(), dataSource);

        return dataSource;
    }

    public static ITreeNode getNavTree() throws Exception {
        List<ITreeNode> children = new ArrayList<ITreeNode>();
        for (DataSource ds : getDataSources()) {
            if (ds instanceof ClassPathDataSource) {
                continue;
            }
            children.add(ds.getNavTree());
        }
        DBObjectImpl root = new DBObjectImpl("ROOT", "根节点", children);
        root.setObjectType(DBObjectType.NONE);
        return root;
    }

    /**
     * 获得类路径数据源
     *
     * @return 返回类路径数据源
     * @since 1.0.0
     */
    public static ClassPathDataSource getClassPathDataSource() {
        ClassPathDataSource classPathDataSource = (ClassPathDataSource) dataSourceMap.get("classpath");
        if (classPathDataSource == null) {
            classPathDataSource = ClassPathDataSource.getInstance();
            dataSourceMap.put("classpath", classPathDataSource);
        }
        return classPathDataSource;
    }

    /**
     * 获得可用的数据库数据源
     *
     * @return 返回可用的数据库数据源
     * @since 1.0.0
     */
    public static List<DBDataSource> getAvailableDbDataSource() {
        List<DBDataSource> result = new ArrayList<DBDataSource>();
        for (DataSource ds : dataSourceMap.values()) {
            if (ds instanceof DBDataSource && ds.isAvailable()) {
                result.add((DBDataSource) ds);
            }
        }
        return result;
    }

    /**
     * 从源数据源导出数据到目标数据源
     *
     * @param source 源数据源
     * @param target 目标数据源
     * @param request 请求对象
     */
    public static void exp(DataSource source, DataSource target, BaseRequest request) throws Exception {
        IResponse response = source.exp(request);
        request.setResponse(response);
        target.imp(request);
    }
}
