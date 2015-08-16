package com.metaui.fxbase.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FormModel {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty displayName = new SimpleStringProperty();
    private IntegerProperty colCount = new SimpleIntegerProperty(3);
    private IntegerProperty colWidth = new SimpleIntegerProperty(180);
    private IntegerProperty labelGap = new SimpleIntegerProperty(5);
    private IntegerProperty fieldGap = new SimpleIntegerProperty(15);
    private IntegerProperty hgap = new SimpleIntegerProperty(3);
    private IntegerProperty vgap = new SimpleIntegerProperty(5);

    private ObservableList<FormFieldModel> formFields = FXCollections.observableList(new ArrayList<>());
    private ObservableList<ActionModel> actions = FXCollections.observableArrayList(new ArrayList<>());

    private Map<String, FormFieldModel> nameMap = new HashMap<>();

    public FormModel() {
        formFields.addListener((ListChangeListener<FormFieldModel>) change -> {
            if (change.next()) {
                // 新增
                if(change.wasAdded()) {
                    List<? extends FormFieldModel> list = change.getAddedSubList();
                    for (FormFieldModel field : list) {
                        nameMap.put(field.getName(), field);
                    }
                }
                // 删除
                if (change.wasRemoved()) {
                    List<? extends FormFieldModel> list = change.getRemoved();
                    for (FormFieldModel field : list) {
                        nameMap.remove(field.getName());
                    }
                }
            }
        });
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

    public int getColCount() {
        return colCount.get();
    }

    public IntegerProperty colCountProperty() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount.set(colCount);
    }

    public int getColWidth() {
        return colWidth.get();
    }

    public IntegerProperty colWidthProperty() {
        return colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth.set(colWidth);
    }

    public int getLabelGap() {
        return labelGap.get();
    }

    public IntegerProperty labelGapProperty() {
        return labelGap;
    }

    public void setLabelGap(int labelGap) {
        this.labelGap.set(labelGap);
    }

    public int getFieldGap() {
        return fieldGap.get();
    }

    public IntegerProperty fieldGapProperty() {
        return fieldGap;
    }

    public void setFieldGap(int fieldGap) {
        this.fieldGap.set(fieldGap);
    }

    public int getHgap() {
        return hgap.get();
    }

    public IntegerProperty hgapProperty() {
        return hgap;
    }

    public void setHgap(int hgap) {
        this.hgap.set(hgap);
    }

    public int getVgap() {
        return vgap.get();
    }

    public IntegerProperty vgapProperty() {
        return vgap;
    }

    public void setVgap(int vgap) {
        this.vgap.set(vgap);
    }

    public ObservableList<FormFieldModel> getFormFields() {
        return formFields;
    }

    public void setFormFields(ObservableList<FormFieldModel> formFields) {
        this.formFields = formFields;
    }

    public ObservableList<ActionModel> getActions() {
        return actions;
    }

    public void setActions(List<ActionModel> actions) {
        this.actions.setAll(actions);
    }

    public FormFieldModel getFieldByName(String name) {
        return nameMap.get(name);
    }
}
