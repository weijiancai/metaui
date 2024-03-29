package com.metaui.fxbase.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * App ViewModel
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class AppModel {
    private StringProperty title = new SimpleStringProperty("系统名称");
    private ObjectProperty<ObservableList<NavMenuModel>> navMenus = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableList<NavMenuModel> getNavMenus() {
        return navMenus.get();
    }

    public ObjectProperty<ObservableList<NavMenuModel>> navMenusProperty() {
        return navMenus;
    }

    public void setNavMenus(ObservableList<NavMenuModel> navMenus) {
        this.navMenus.set(navMenus);
    }
}
