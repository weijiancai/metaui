package com.metaui.core.ui.config;

import com.metaui.core.config.ProjectConfig;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.ui.IViewConfig;
import com.metaui.core.ui.config.layout.FormConfig;

/**
 * 初始化配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ConfigInit {
    public static IViewConfig getProjectConfig() {
        IViewConfig viewConfig = ViewConfigFactory.createFormConfig(MetaManager.getMeta(ProjectConfig.class));
        FormConfig formConfig = new FormConfig(viewConfig.getLayoutConfig());
        formConfig.setColCount(1);
        return viewConfig;
    }
}
