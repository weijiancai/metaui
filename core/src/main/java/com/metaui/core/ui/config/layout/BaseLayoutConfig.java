package com.metaui.core.ui.config.layout;

import com.metaui.core.ui.ConfigConst;
import com.metaui.core.ui.ILayoutConfig;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseLayoutConfig<T> implements ConfigConst, Comparable<T> {
    protected ILayoutConfig config;

    public BaseLayoutConfig(ILayoutConfig config) {
        this.config = config;
    }

    public String getStringValue(String propName) {
        return config.getPropStringValue(propName);
    }

    public int getIntValue(String propName) {
        return config.getPropIntValue(propName);
    }

    public boolean getBooleanValue(String propName) {
        return config.getPropBooleanValue(propName);
    }

    public void setValue(String propName, String propValue) {
        config.setPropValue(propName, propValue);
    }

    public ILayoutConfig getLayoutConfig() {
        return config;
    }
}
