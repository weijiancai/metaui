package com.metaui.fxbase.desktop;

import com.metaui.fxbase.ui.view.MUTabPane;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.model.NavMenuModel;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列表导航 tabs桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUListViewTabsDesktop extends BorderPane {
    private AppModel app;
    private Map<String, Tab> tabCache = new HashMap<>();
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
        listView.itemsProperty().bind(app.navMenusProperty());
        listView.setOnMouseClicked(event -> {
            NavMenuModel model = listView.getSelectionModel().getSelectedItem();
            Tab tab = tabCache.get(model.getId());
            if (tab == null) {
                tab = new Tab();
                tab.idProperty().bind(model.idProperty());
                tab.textProperty().bind(model.titleProperty());
//                tab.setClosable(false);
                tab.setContent((Node)model.getView());

                tabPane.getTabs().add(tab);
                tabCache.put(model.getId(), tab);
            }
            // 选择当前
            tabPane.getSelectionModel().select(tab);
        });
        this.setLeft(listView);
    }

    private void createTabPane() {
        tabPane = new MUTabPane();
        Tab desktopTab = new Tab("桌面");
        desktopTab.setClosable(false);
        tabPane.getTabs().add(desktopTab);
        // 监听tab删除，删除时从缓存中移除
        tabPane.getTabs().addListener((ListChangeListener<Tab>) change -> {
            if(change.next() && change.wasRemoved()) {
                List<? extends Tab> removed = change.getRemoved();
                if(removed.size() > 0) {
                    for (Tab tab : removed) {
                        tabCache.remove(tab.getId());
                    }
                }
            }
        });

        this.setCenter(tabPane);
    }
}
