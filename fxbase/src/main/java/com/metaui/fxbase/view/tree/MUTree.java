package com.metaui.fxbase.view.tree;

import com.metaui.core.ui.IView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * @author wei_jc
 * @since 1.0
 */
public class MUTree extends TreeView<TreeNodeModel> implements IView {
    public MUTree() {

    }

    public MUTree(TreeNodeModel root) {
        super(new MUTreeItem(root));
    }

    @Override
    public void initUI() {
        this.setCellFactory(new Callback<TreeView<TreeNodeModel>, TreeCell<TreeNodeModel>>() {
            @Override
            public TreeCell<TreeNodeModel> call(TreeView<TreeNodeModel> param) {
                return new TextTreeCell();
            }
        });
    }
}
