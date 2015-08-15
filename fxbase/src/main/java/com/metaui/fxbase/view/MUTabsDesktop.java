package com.metaui.fxbase.view;

import com.metaui.fxbase.model.DeskModel;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabsDesktop extends BorderPane {
    private MUNavMenu navMenu;
    private MUTabPane tabPane;

    private DeskModel model;

    public MUTabsDesktop(DeskModel model) {
        this.model = model;

        navMenu = new MUNavMenu(model.getNavMenuModel());
        tabPane = new MUTabPane(model.getTabPaneModel());

        this.setLeft(navMenu);
        this.setCenter(tabPane);
    }
}
