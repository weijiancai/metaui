package com.metaui.ideaplugin;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * MetaUI Application Component
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/16.
 */
public class MetaUIPlugin implements ApplicationComponent {
    public MetaUIPlugin() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "MetaUIPlugin";
    }
}
