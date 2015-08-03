package com.metaui.fxbase.vm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * App ViewModel
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class AppVM {
    private StringProperty title = new SimpleStringProperty("系统名称");
    private ObjectProperty<ObservableList<NavMenuVM>> navMenuList = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableList<NavMenuVM> getNavMenuList() {
        return navMenuList.get();
    }

    public ObjectProperty<ObservableList<NavMenuVM>> navMenuListProperty() {
        return navMenuList;
    }

    public void setNavMenuList(ObservableList<NavMenuVM> navMenuList) {
        this.navMenuList.set(navMenuList);
    }
}
