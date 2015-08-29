package com.metaui.fxbase.view.tree;

import com.metaui.core.model.ITreeNode;
import com.metaui.core.observer.EventData;
import com.metaui.core.observer.Subject;
import com.metaui.core.ui.model.View;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author wei_jc
 * @since 1.0
 */
public class TreeNodeModel implements ITreeNode<TreeNodeModel> {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty pid = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty displayName = new SimpleStringProperty();
    private StringProperty icon = new SimpleStringProperty();
    private StringProperty presentableText = new SimpleStringProperty();
    private BooleanProperty isVirtual = new SimpleBooleanProperty();
    private BooleanProperty isLeaf = new SimpleBooleanProperty();
    private IntegerProperty sortNum = new SimpleIntegerProperty();

    private ObjectProperty<TreeNodeModel> parent;
    private ObservableList<TreeNodeModel> children;

    public TreeNodeModel() {
    }

    public TreeNodeModel(String displayName) {
        setDisplayName(displayName);
    }

    public TreeNodeModel(String displayName, String presentableText) {
        setDisplayName(displayName);
        setPresentableText(presentableText);
    }

    @Override
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    @Override
    public String getPid() {
        return pid.get();
    }

    public StringProperty pidProperty() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid.set(pid);
    }

    @Override
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String getDisplayName() {
        return displayName.get();
    }

    public StringProperty displayNameProperty() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    @Override
    public String getIcon() {
        return icon.get();
    }

    @Override
    public View getView() {
        return null;
    }

    public StringProperty iconProperty() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon.set(icon);
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }

    @Override
    public Subject<EventData> getPresentableTextSubject() {
        return null;
    }

    public BooleanProperty isVirtualProperty() {
        return isVirtual;
    }

    public void setVirtual(boolean isVirtual) {
        this.isVirtual.set(isVirtual);
    }

    @Override
    public boolean isLeaf() {
        return isLeaf.get();
    }

    public BooleanProperty isLeafProperty() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf.set(isLeaf);
    }

    @Override
    public String getPresentableText() {
        return presentableText.get();
    }

    public StringProperty presentableTextProperty() {
        return presentableText;
    }

    public void setPresentableText(String presentableText) {
        this.presentableText.set(presentableText);
    }

    @Override
    public int getSortNum() {
        return sortNum.get();
    }

    public IntegerProperty sortNumProperty() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum.set(sortNum);
    }

    @Override
    public TreeNodeModel getParent() {
        return parent.get();
    }

    public ObjectProperty<TreeNodeModel> parentProperty() {
        return parent;
    }

    public void setParent(TreeNodeModel parent) {
        this.parent.set(parent);
    }

    @Override
    public ObservableList<TreeNodeModel> getChildren() {
        if (children == null) {
            children = FXCollections.observableArrayList();
        }
        return children;
    }

    public void setChildren(ObservableList<TreeNodeModel> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return displayName.get();
    }

    public void onExpanded(TreeNodeModel parent, Boolean newValue) {

    }
}
