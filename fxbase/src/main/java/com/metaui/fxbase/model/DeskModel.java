package com.metaui.fxbase.model;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DeskModel {
    private NavMenuModel navMenuModel;
    private TabPaneModel tabPaneModel;

    public TabPaneModel getTabPaneModel() {
        return tabPaneModel;
    }

    public void setTabPaneModel(TabPaneModel tabPaneModel) {
        this.tabPaneModel = tabPaneModel;
    }

    public NavMenuModel getNavMenuModel() {
        return navMenuModel;
    }

    public void setNavMenuModel(NavMenuModel navMenuModel) {
        this.navMenuModel = navMenuModel;
    }
}
