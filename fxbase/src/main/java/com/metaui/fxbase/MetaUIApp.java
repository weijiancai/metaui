package com.metaui.fxbase;

import com.metaui.core.config.ProjectConfig;
import com.metaui.core.config.SystemManager;
import com.metaui.fxbase.facade.IFacade;
import com.metaui.fxbase.facade.impl.BaseFacade;
import com.metaui.fxbase.ui.IDesktop;
import com.metaui.fxbase.ui.view.MUTabsDesktop;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaUIApp extends BaseApp {
    @Override
    public IFacade getFacade() {
        return new BaseFacade() {
            @Override
            protected void initProjectConfig() throws Exception {

            }

            @Override
            public ProjectConfig getProjectConfig() throws Exception {
                return SystemManager.getInstance().createProjectConfig("MetaUI");
            }

            @Override
            public void initAfter() throws Exception {

            }

            @Override
            public IDesktop getDesktop() throws Exception {
                return new MUTabsDesktop();
            }
        };
    }

    public static void main(String[] args) {
        launch(MetaUIApp.class);
    }
}
