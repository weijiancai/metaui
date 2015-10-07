package com.metaui.fxbase;

import com.metaui.core.config.ProjectConfig;
import com.metaui.fxbase.BaseApp;
import com.metaui.fxbase.facade.IFacade;
import com.metaui.fxbase.ui.IDesktop;


/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxAppTest extends BaseApp {
    @Override
    public IFacade getFacade() {
        return new IFacade() {
            @Override
            public ProjectConfig getProjectConfig() throws Exception {
                ProjectConfig projectConfig = new ProjectConfig();
                projectConfig.setName("JavaFx App Test");
                projectConfig.setDisplayName("JavaFx App Test");
                return projectConfig;
            }

            @Override
            public void initAfter() throws Exception {

            }

            @Override
            public IDesktop getDesktop() throws Exception {
                return new FxTestDesktop();
            }
        };
    }

    public static void main(String[] args) {
        launch(FxAppTest.class);
    }
}
