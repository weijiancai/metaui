package com.metaui.core.moudle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块管理器
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class ModuleManager {
    private static final Logger log = LoggerFactory.getLogger(ModuleManager.class);
    private static ModuleManager instance;
    private List<IModule> modules = new ArrayList<IModule>();

    private ModuleManager() {}

    public static ModuleManager getInstance() {
        if (instance == null) {
            instance = new ModuleManager();
        }
        return instance;
    }

    /**
     * 注册模块
     *
     * @param module 模块
     */
    public void regist(IModule module) {
        log.info("注册模块：{}", module.getName());
        modules.add(module);
    }

    /**
     * 获得所有模块
     *
     * @return 返回所有模块
     */
    public List<IModule> getModules() {
        return modules;
    }
}
