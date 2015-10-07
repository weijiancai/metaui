package com.metaui.fxbase.view.tree;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0
 */
public class MUTreeItem extends TreeItem<TreeNodeModel> {
    private Map<TreeNodeModel, MUTreeItem> nodeMap = new HashMap<>();
    private TreeNodeModel model;
    public MUTreeItem(TreeNodeModel model) {
        super(model);
        this.model = model;

        init();
    }

    private void init() {
        // 监听子节点的变化
        model.getChildren().addListener(new ListChangeListener<TreeNodeModel>() {
            @Override
            public void onChanged(Change<? extends TreeNodeModel> c) {
                if (c.next()) {
                    // 新增
                    List<? extends TreeNodeModel> addList = c.getAddedSubList();
                    addChildren(addList);
                    // 删除
                    List<? extends TreeNodeModel> deleteList = c.getRemoved();
                    removeChildren(deleteList);
                }
            }
        });

        // 初始化已有的子节点
        addChildren(model.getChildren());
        // 监听节点是否展开
        this.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.onExpanded(model, newValue);
            }
        });
    }

    @Override
    public ObservableList<TreeItem<TreeNodeModel>> getChildren() {
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        return model.isLeaf();
    }

    public void addChildren(List<? extends TreeNodeModel> list) {
        for (TreeNodeModel child : list) {
            System.out.println("add child Tree Node: " + child.getDisplayName());
            MUTreeItem treeItem = new MUTreeItem(child);
            nodeMap.put(child, treeItem);
            getChildren().add(treeItem);
        }
    }

    public void removeChildren(List<? extends TreeNodeModel> list) {
        for (TreeNodeModel child : list) {
            MUTreeItem treeItem = nodeMap.remove(child);
            getChildren().remove(treeItem);
        }
    }
}
