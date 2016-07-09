package com.metaui.fxbase.model;

import com.metaui.core.util.Callback;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Action模型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ActionModel {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty displayName = new SimpleStringProperty();
    private IntegerProperty sortNum = new SimpleIntegerProperty();

    private Callback<Void, Void> callback;

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDisplayName() {
        return displayName.get();
    }

    public StringProperty displayNameProperty() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    public int getSortNum() {
        return sortNum.get();
    }

    public IntegerProperty sortNumProperty() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum.set(sortNum);
    }

    public Callback<Void, Void> getCallback() {
        return callback;
    }

    public void setCallback(Callback<Void, Void> callback) {
        this.callback = callback;
    }

    public static ActionModelBuilder builder() {
        return ActionModelBuilder.create();
    }
}
