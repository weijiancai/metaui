package com.metaui.eshop.moudle;

import com.metaui.core.config.PathManager;
import com.metaui.core.moudle.IModule;

import java.io.File;

/**
 * 网店模块
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class EShopModule implements IModule {
    /**
     * 模块名称
     */
    public static final String NAME = "eshop";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void init() {

    }

    /**
     * 获得网店模块路径
     *
     * @return 返回网店模块路径
     */
    public static File getPath() {
        return PathManager.getModulePath(NAME);
    }
}
