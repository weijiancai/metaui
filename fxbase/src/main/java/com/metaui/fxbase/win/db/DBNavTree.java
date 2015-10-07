package com.metaui.fxbase.win.db;

import com.metaui.core.ui.IView;
import com.metaui.fxbase.view.tree.MUTree;
import com.metaui.fxbase.view.tree.MUTreeItem;
import com.metaui.fxbase.view.tree.TextTreeCell;
import com.metaui.fxbase.view.tree.TreeNodeModel;
import com.metaui.fxbase.win.db.model.DBNavTreeModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * 数据库导航树
 *
 * @author wei_jc
 * @since 1.0
 */
public class DBNavTree extends MUTree implements IView {
    private DBNavTreeModel model;

    public DBNavTree() {
    }

    @Override
    public void initUI() {
        super.initUI();
        model = new DBNavTreeModel();
        this.setRoot(new MUTreeItem(model.getRoot()));
        // 重写单元格显示样式
        this.setCellFactory(param -> new DBNavTreeCell());
    }
}
