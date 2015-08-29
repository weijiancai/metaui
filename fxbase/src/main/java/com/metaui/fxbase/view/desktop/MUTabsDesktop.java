package com.metaui.fxbase.view.desktop;

import com.metaui.core.ui.IView;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.model.NavMenuModel;
import com.metaui.fxbase.ui.view.MUTabPane;
import com.metaui.fxbase.view.tree.MUTree;
import com.metaui.fxbase.view.tree.TreeNodeModel;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabs 桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabsDesktop extends BorderPane implements IView {
    private AppModel app;
    private Map<String, Tab> tabCache = new HashMap<>();
    protected MUTabPane tabPane;
    private MUTree navTree;

    public MUTabsDesktop(AppModel app) {
        this.app = app;
    }

    @Override
    public void initUI() {
        createLeft();
        createTabPane();
    }

    public void createLeft() {
        this.setLeft(getNavTree());
    }

    public MUTree getNavTree() {
        if (navTree == null) {
            navTree = new MUTree();
        }
        return navTree;
    }

    private void createNavMenu() {
        ListView<NavMenuModel> listView = new ListView<>();
        listView.itemsProperty().bind(app.navMenusProperty());
        listView.setOnMouseClicked(event -> {
            NavMenuModel model = listView.getSelectionModel().getSelectedItem();
            if (model == null) {
                return;
            }
            Tab tab = tabCache.get(model.getId());
            if (tab == null) {
                tab = new Tab();
                tab.idProperty().bind(model.idProperty());
                tab.textProperty().bind(model.titleProperty());
//                tab.setClosable(false);
                tab.setContent((Node) model.getView());

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
