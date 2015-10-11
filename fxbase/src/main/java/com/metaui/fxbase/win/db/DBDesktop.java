package com.metaui.fxbase.win.db;

import com.metaui.core.datasource.db.object.DBObject;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.ui.IView;
import com.metaui.core.ui.model.View;
import com.metaui.fxbase.MuEventHandler;
import com.metaui.fxbase.model.AppModel;
import com.metaui.fxbase.view.desktop.MUTabsDesktop;
import com.metaui.fxbase.view.table.MUTable;
import com.metaui.fxbase.view.tree.TreeNodeModel;
import com.metaui.fxbase.win.db.model.DBTreeNodeModel;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * 数据库桌面
 *
 * @author wei_jc
 * @since 1.0
 */
public class DBDesktop extends MUTabsDesktop implements IView {
    private DBNavView navView;
    private DBSearchBox searchBox;

    public DBDesktop(AppModel app) {
        super(app);
    }

    @Override
    public Node getLeftNode() {
        navView = new DBNavView();
        navView.initUI();

        // 搜索框
        searchBox = new DBSearchBox(view -> {
            openTab(view);
            return null;
        });
        // 注册键盘按键事件
        this.addEventHandler(KeyEvent.ANY, new MuEventHandler<KeyEvent>() {
            @Override
            public void doHandler(KeyEvent event) throws Exception {
                if (event.isControlDown() && event.getCode() == KeyCode.N) {
                    TreeItem<TreeNodeModel> selectedItem = navView.getNavTree().getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        DBTreeNodeModel model = (DBTreeNodeModel) selectedItem.getValue();
                        DBObject object = model.getDbObject();
                        if (object != null) {
                            searchBox.setDataSource(object.getDataSource());
                            if (object.getSchema() != null) {
                                searchBox.setSchema(object.getSchema().getName());
                            }
                        }
                    }

                    searchBox.show();
                }
            }
        });

        return navView;
    }

    @Override
    public void initUI() {
        super.initUI();
        // SQL控制台
        Tab sqlConsoleTab = new Tab("SQL控制台");
        sqlConsoleTab.setContent(new DBSqlConsoleWin());
        sqlConsoleTab.setClosable(false);
        tabPane.getTabs().add(sqlConsoleTab);

        navView.getNavTree().setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    DBTreeNodeModel model = (DBTreeNodeModel) navView.getNavTree().getSelectionModel().getSelectedItem().getValue();
                    DBObject object = model.getDbObject();
                    if (object.getObjectType() == DBObjectType.VIEW || object.getObjectType() == DBObjectType.TABLE) {
                        openTab(object.getView());
                    }
                }
            }
        });
    }

    public void openTab(View view) {
        MUTable table = new MUTable(view.getMeta());
        table.getModel().setEditable(true);
        String name = view.getMeta().getResource().getName();

        Tab tab = new Tab(name);
        tab.setId(view.getId());
        tab.setContent(table);
        addTab(tab);
    }
}
