package com.metaui.fxbase.desktop;

import com.metaui.fxbase.vm.AppVM;
import com.metaui.fxbase.vm.NavMenuVM;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUListViewTabsDesktop extends BorderPane {
    private AppVM app;

    public MUListViewTabsDesktop(AppVM app) {
        this.app = app;

        init();
    }

    private void init() {
        createNavMenu();
    }

    private void createNavMenu() {
        ListView<NavMenuVM> listView = new ListView<>();
        listView.itemsProperty().bind(app.navMenuListProperty());
        this.setLeft(listView);
    }
}
