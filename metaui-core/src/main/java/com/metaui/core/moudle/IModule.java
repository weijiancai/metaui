package com.metaui.core.moudle;

/**
 * 模块接口
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public interface IModule {
    /**
     * 获得模块名称
     *
     * @return 返回模块名称
     */
    String getName();

    /**
     * 模块初始化，系统启动时，会调用模块的初始化方法
     */
    void init();
}
