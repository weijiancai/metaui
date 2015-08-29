package com.metaui.core.model.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.metaui.core.model.ITreeNode;
import com.metaui.core.observer.BaseSubject;
import com.metaui.core.observer.EventData;
import com.metaui.core.observer.Subject;
import com.metaui.core.ui.model.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class BaseTreeNode implements ITreeNode {
    private String id;
    private String pid;
    private String name;
    private String displayName;
    private String presentableText = "";
    private int sortNum;
    private View view;

    private ITreeNode parent;
    private List<ITreeNode> children;

    private Subject<EventData> presentableTextSubject = new BaseSubject<EventData>();

    public BaseTreeNode() {}

    public BaseTreeNode(String displayName) {
        this.displayName = displayName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPresentableText(String presentableText) {
        if (!this.presentableText.equals(presentableText)) {
            EventData eventData = new EventData();
            eventData.setStrData(presentableText);
            presentableTextSubject.notifyObserver(eventData);
        }
        this.presentableText = presentableText;
    }

    public void setParent(ITreeNode parent) {
        this.parent = parent;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPid() {
        return pid;
    }

    @Override
    public ITreeNode getParent() {
        return parent;
    }

    @Override
    public List<ITreeNode> getChildren() {
        if (children == null) {
            children = new ArrayList<ITreeNode>();
        }
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int getSortNum() {
        return sortNum;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }

    @Override
    @JSONField(serialize = false)
    public View getView() {
        return view;
    }

    @Override
    public String getPresentableText() {
        return presentableText;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Subject<EventData> getPresentableTextSubject() {
        return presentableTextSubject;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void addChild(ITreeNode node) {
        node.setParent(this);
        getChildren().add(node);
    }
}
