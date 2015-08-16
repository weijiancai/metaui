package com.metaui.fxbase.model;

import com.metaui.core.ui.IView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 导航菜单ViewModel
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class NavMenuModel {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private IView view;

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public IView getView() {
        return view;
    }

    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return title.get();
    }
}
