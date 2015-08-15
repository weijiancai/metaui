package com.metaui.fxbase.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 导航菜单ViewModel
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class NavMenuModel {
    private StringProperty title = new SimpleStringProperty();
    private IViewModel viewModel;

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public IViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public String toString() {
        return title.get();
    }
}
