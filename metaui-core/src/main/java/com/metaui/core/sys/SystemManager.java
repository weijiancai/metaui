package com.metaui.core.sys;

import com.metaui.core.moudle.IModule;
import com.metaui.core.moudle.ModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统管理器
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class SystemManager {
    private static Logger log = LoggerFactory.getLogger(SystemManager.class);

    private static SystemManager instance;

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

        log.info("初始化模块.......");
        // 得到所有模块配置类
        for (IModule module : ModuleManager.getInstance().getModules()) {
            module.init();
        }
    }
}
