package com.metaui.fxbase.desktop;

import com.metaui.fxbase.ui.view.MUTabPane;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.model.NavMenuModel;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUListViewTabsDesktop extends BorderPane {
    private AppModel app;

    protected MUTabPane tabPane;

    public MUListViewTabsDesktop(AppModel app) {
        this.app = app;

        init();
    }

    private void init() {
        createNavMenu();
        createTabPane();
    }

    private void createNavMenu() {
        ListView<NavMenuModel> listView = new ListView<>();
        listView.itemsProperty().bind(app.navMenuListProperty());
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tab tab = new Tab(listView.getSelectionModel().getSelectedItem().getTitle());
                tab.setClosable(false);
                tabPane.getTabs().add(tab);
            }
        });
        this.setLeft(listView);
    }

    private void createTabPane() {
        tabPane = new MUTabPane();
        Tab tab = new Tab("桌面");
        tab.setClosable(false);
        tabPane.getTabs().add(tab);

        this.setCenter(tabPane);
    }
}
