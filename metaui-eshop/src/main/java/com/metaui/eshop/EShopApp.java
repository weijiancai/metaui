package com.metaui.eshop;

import com.metaui.core.moudle.ModuleManager;
import com.metaui.core.sys.SystemManager;
import com.metaui.eshop.moudle.EShopModule;

/**
 * 网店应用程序入口
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class EShopApp {
    public static void main(String[] args) throws Exception {
        // 注册模块
        ModuleManager.getInstance().regist(new EShopModule());
        // 系统初始化
        SystemManager.getInstance().init();
    }
}
