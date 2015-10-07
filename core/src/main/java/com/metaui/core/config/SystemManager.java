package com.metaui.core.config;

import com.metaui.core.datasource.DataSource;
import com.metaui.core.datasource.DataSourceManager;
import com.metaui.core.datasource.ResourceItem;
import com.metaui.core.datasource.classpath.ClassPathDataSource;
import com.metaui.core.datasource.classpath.ClassPathResourceItem;
import com.metaui.core.datasource.db.DBDataSource;
import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.datasource.db.util.JdbcTemplate;
import com.metaui.core.datasource.persist.IPDB;
import com.metaui.core.datasource.persist.MetaPDBFactory;
import com.metaui.core.datasource.persist.MetaRowMapperFactory;
import com.metaui.core.dict.DictManager;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.project.ProjectManager;
import com.metaui.core.ui.ViewManager;
import com.metaui.core.ui.config.LayoutConfig;
import com.metaui.core.ui.layout.LayoutManager;
import com.metaui.core.util.HSqlDBServer;
import com.metaui.core.util.UFile;
import com.metaui.core.util.UIO;
import com.metaui.core.util.jaxb.JAXBUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static com.metaui.core.config.SystemConfig.*;

/**
 * @author wei_jc
 * @version 1.0
 */
public class SystemManager {
    private static Logger log;

    private static SystemManager instance;
    private static SystemInfo sysInfo;
    private static Map<String, ProjectConfig> cache = new HashMap<String, ProjectConfig>();
    private static Map<String, String> settingMap = new HashMap<String, String>();
    private static List<ProfileSetting> settingList = new ArrayList<ProfileSetting>();
    public static Properties metaProp = new Properties();
    private LayoutConfig layoutConfig;

    static {
        try {
            // 设置日志目录属性
            System.setProperty("logs_dir", PathManager.getLogPath().getAbsolutePath());
            PropertyConfigurator.configure(UIO.getInputStream("/log4j.properties", UIO.FROM.CP));

            log = Logger.getLogger(SystemManager.class);
            DIR_SYSTEM = UFile.makeDirs(PathManager.getHomePath(), SYSTEM_NAME);
            DIR_SYSTEM_HSQL_DB = UFile.makeDirs(DIR_SYSTEM, DIR_NAME_SQLDB);

            log.info("======================== 系统信息 ==========================================");
            log.info("系统默认目录：" + DIR_SYSTEM.getAbsolutePath());
            log.info("类路径目录：" + DIR_CLASS_PATH.getAbsolutePath());
            log.info("日志目录：" + PathManager.getLogPath().getAbsolutePath());
            log.info("==========================================================================");

            // 加载系统信息
            sysInfo = new SystemInfo();

            // 加载系统属性
            metaProp.load(UIO.getInputStream("/meta.properties", UIO.FROM.CP));
            // 加载系统信息
            sysInfo.load();
            // 加载项目
            loadProjectConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }

        return instance;
    }

    /**
     * 初始化系统
     */
    public void init() throws Exception {
        log.info("正在初始化系统...................");
        // 启动数据库
//        HSqlDBServer.getInstance().start();

        // 加载系统数据源
        DataSourceManager.getSysDataSource();
        // 检查数据库版本
        checkDbVersion();
        // 加载参数配置
        loadProfileSetting();
        // 加载数据源
        loadDataSource();
        // 加载数据字典
        DictManager.load();
        // 加载布局配置
        LayoutManager.load();
        // 加载元数据
        MetaManager.load();
        // 加载视图
        ViewManager.load();
        // 加载项目
//        ProjectManager.load();

        switch (SystemConfig.SYSTEM_TYPE) {
            case DESKTOP: {
                // 注册Action
                // MUActionConfig.getInstance().addAction(new MUAction(MetaManager.getMeta("MobileNumber"), "downloadMobileNumber", "下载手机号", MobileNumberAction.class, "fetchMobileNumber"));
//                MetaManager.getMeta("MobileNumber").addAction(new MUAction(MetaManager.getMeta("MobileNumber"), "downloadMobileNumber", "下载手机号", MobileNumberAction.class, "fetchMobileNumber"));
                break;
            }
            case WEB: {

            }
        }
        log.info("初始化系统成功！");
    }

    private static void loadProjectConfig() throws Exception {
        // 遍历所有项目
        File[] files = SystemConfig.DIR_SYSTEM.listFiles();
        if (files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    File projectConfigFile = new File(file, FILE_NAME_PROJECT_CONFIG);
                    if (projectConfigFile.exists()) {
                        ProjectConfig projectConfig = JAXBUtil.unmarshal(projectConfigFile, ProjectConfig.class);
                        cache.put(projectConfig.getName(), projectConfig);
                    }
                }
            }
        }
    }

    /**
     * 加载数据源
     */
    private void loadDataSource() throws Exception {
        // 加载系统默认Hsqldb数据源
//        File sysDbFile = UFile.createFile(DIR_SYSTEM_HSQL_DB, SYS_DB_NAME);
//        HSqlDBServer.getInstance().addDbFile(SYS_DB_NAME, sysDbFile.getAbsolutePath());
//        DataSourceManager.addDataSource(DataSourceManager.getSysDataSource());
//        DataSourceManager.addDataSource(ClassPathDataSource.getInstance());

        /*for (ProjectConfig config : cache.values()) {
            List<DBDataSource> list = config.getDataSources();
            if (list != null) {
                for (DBDataSource dataSource : list) {
                    DataSourceManager.addDataSource(dataSource);
                    // 如果存在hsqldb文件路径，则加载hsqldb数据库文件
                    if (dataSource.getFilePath() != null) {
                        HSqlDBServer.getInstance().addDbFile(dataSource.getName(), dataSource.getFilePath());
                    }
                }
            }
        }

        for (DataSource ds : DataSourceManager.getDataSources()) {
            log.info("==============加载数据源： " + ds.getName());
            ds.load();
        }*/

        JdbcTemplate template = new JdbcTemplate();
        List<DataSource> list = template.query("select * from mu_db_datasource", MetaRowMapperFactory.getDataSource());
        for (DataSource ds : list) {
            log.info("添加数据源【" + ds.getName() + "】");
            DataSourceManager.addDataSource(ds);
        }
    }

    private void loadProfileSetting() throws Exception{
        log.info("加载参数配置......");
        JdbcTemplate template = new JdbcTemplate();
        settingList = template.query("select * from mu_profile_setting", MetaRowMapperFactory.getProfileSetting());
        for (ProfileSetting setting : settingList) {
            settingMap.put(setting.getConfSection() + "_" + setting.getConfKey(), setting.getConfValue());
        }
    }

    private void checkDbVersion() {
        try {
            DBDataSource dataSource = DataSourceManager.getSysDataSource();
            // 获得数据库当前系统的最大版本号
            String maxVersion = dataSource.getMaxVersion(SystemConfig.SYSTEM_NAME);
            DatabaseType dbType = dataSource.getDatabaseType();
            log.info("当前系统版本为：" + maxVersion + ", 数据库为：" + dbType.getDisplayName());
            // 获得升级目录下升级脚本
            URL url = getClass().getResource("/db_upgrade");
            if (url == null) {
                System.err.println(String.format("没有数据库升级脚本目录【%s】，系统将退出！", "\\db_upgrade"));
                System.exit(0);
            }
            File upgradeDir = new File(url.getPath());
            File[] files = upgradeDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 如果是文件，则继续
                    if (file.isFile()) {
                        continue;
                    }
                    // 取目录，目录名是版本号
                    final String version = file.getName();
                    if (maxVersion.compareTo(version) < 0) {
                        log.info("检测到新版本需要升级：" + version);
                        String path;
                        String sql;
                        if ("0.0.0".equals(maxVersion)) {
                            path = "/db_upgrade/init_" + dbType.getName().toLowerCase() + ".sql";
                            sql = UFile.readStringFromCP(path);
                            if (sql != null) {
                                dataSource.getDbConnection().execSqlScript(sql, "\\$\\$");
                            }
                        }
                        path = "/db_upgrade/" + version + "/" + SYSTEM_NAME + "_" + dbType.getName().toLowerCase() + ".sql";
                        log.info("升级脚本路径：" + path);
                        sql = UFile.readStringFromCP(path);
                        if (sql == null) {
                            System.err.println(String.format("升级文件【%s】不存在，请检查！", path));
                            System.exit(0);
                        }
                        log.info("开始升级......");
                        dataSource.getDbConnection().execSqlScript(sql, ";");
                        // 插入新的数据库版本
                        dataSource.save(new IPDB() {
                            @Override
                            public Map<String, ? extends Map<String, Object>> getPDBMap() {
                                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("sys_name", SYSTEM_NAME);
                                map.put("db_version", version);
                                map.put("input_date", new Date());
                                map.put("memo", "系统自动升级到" + version);
                                result.put(SYS_DB_VERSION_TABLE_NAME, map);
                                return result;
                            }
                        });
                        log.info("升级完成");
                    }
                }
            }
        } catch (Exception e) {
            log.error("检查数据库失败！", e);
            System.exit(0);
        }
    }

    public ProjectConfig createProjectConfig(String projectName) throws Exception {
        ProjectConfig projectConfig = new ProjectConfig(projectName);
        Class<?>[] clazz = new Class[]{ProjectConfig.class, DBDataSource.class};
        JAXBUtil.marshalToFile(projectConfig, projectConfig.getProjectConfigFile(), clazz);

        return projectConfig;
    }

    public ProjectConfig getProjectConfig(String projectName) {
        return cache.get(projectName);
    }

    public List<ProjectConfig> getProjectConfigs() {
        return new ArrayList<ProjectConfig>(cache.values());
    }

    public static void save(ProjectConfig projectConfig) throws Exception {
        JAXBUtil.marshalToFile(projectConfig, projectConfig.getProjectConfigFile(), ProjectConfig.class, DBDataSource.class);
    }

    /**
     * 获得布局配置
     *
     * @return 返回布局配置
     */
    public LayoutConfig getLayoutConfig() {
        return layoutConfig;
    }

    /**
     * 检查项目是否已经配置
     * 1. 检查项目名称
     * 2. 检查sys数据源
     *
     * @param projectConfig 项目配置信息
     * @return 如果已经配置，返回true，否则返回false
     */
    public static boolean isConfigured(ProjectConfig projectConfig) throws Exception {
        // 检查项目名称
        /*if (UString.isEmpty(projectConfig.getName())) {
            return false;
        }
        // 检查sys数据源
        if (DBManager.getDataSource(SystemConfig.SYS_DB_NAME) == null) {
            return false;
        }
        // 检查版本表
        if (!DBUtil.existsTable(SystemConfig.SYS_DB_VERSION_TABLE_NAME)) {
            return false;
        }*/

        return true;
    }

    /**
     * 获得系统信息
     *
     * @return 返回系统信息
     */
    public static SystemInfo getSystemInfo() {
        return sysInfo;
    }

    public static void saveSetting(ProfileSetting setting) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("conf_section", setting.getConfSection());
        param.put("conf_key", setting.getConfKey());
        JdbcTemplate.delete(DataSourceManager.getSysDataSource(), param, "mu_profile_setting");
        JdbcTemplate.save(DataSourceManager.getSysDataSource(), MetaPDBFactory.getProfileSetting(setting));
    }

    public static String getSettingValue(String section, String key) {
        return settingMap.get(section + "_" + key);
    }

    /**
     * 获得系统配置信息
     *
     * @return 返回系统配置信息列表
     * @since 1.0.0
     */
    public static List<ProfileSetting> getSettingList() {
        return settingList;
    }

    /**
     * 重置系统到默认配置，即恢复到出厂设置
     */
    public static void resetToDefaultSetting() throws SQLException {
        JdbcTemplate template = new JdbcTemplate();
        template.clearTable("mu_db_version", "mu_profile_setting");
        template.commit();
    }

    public static String getSystemProperty(String propName) {
        return metaProp.getProperty(propName);
    }
}
