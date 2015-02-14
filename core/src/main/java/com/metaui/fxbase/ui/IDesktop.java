package com.metaui.fxbase.ui;

import com.metaui.fxbase.ui.view.MUTree;
import javafx.scene.Parent;

/**
 * 桌面接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IDesktop {
    /**
     * 初始化UI控件
     *
     * @since 1.0.0
     */
    void initUI();

    /**
     * 初始化UI之后调用
     */
    void initAfter();

    /**
     * 获得桌面UI控件
     *
     * @return 返回桌面
     */
    Parent getDesktop();

    /**
     * 获得导航树
     *
     * @return 返回导航树
     * @since 1.0.0
     */
    MUTree getNavTree();
}
