package com.metaui.core.ui;

/**
 * UI布局Action接口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IActionConfig extends Cloneable {
    /**
     * 获得Action Id
     *
     * @return 返回Action Id
     */
    String getId();

    /**
     * 获得Action名称
     *
     * @return 返回Action名称
     */
    String getName();

    /**
     * 获得Action显示名
     *
     * @return 返回Action显示名
     */
    String getDisplayName();

    /**
     * 获得Action排序号
     *
     * @return 返回Action排序号
     */
    int getSortNum();

    /**
     * Clone布局Action配置
     *
     * @return 返回Clone的布局Action配置
     */
    IActionConfig clone();
}
