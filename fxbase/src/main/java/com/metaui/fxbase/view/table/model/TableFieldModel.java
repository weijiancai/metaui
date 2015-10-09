package com.metaui.fxbase.view.table.model;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.fxbase.model.FormFieldModelBuilder;
import javafx.beans.property.*;
import javafx.util.Callback;

/**
 * 表格字段模型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableFieldModel {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty displayName = new SimpleStringProperty();
    private IntegerProperty width = new SimpleIntegerProperty();
    private BooleanProperty isDisplay = new SimpleBooleanProperty(true);
    private BooleanProperty isPk = new SimpleBooleanProperty(false);
    private BooleanProperty isFk = new SimpleBooleanProperty(false);
    private BooleanProperty editable = new SimpleBooleanProperty(true);
    private IntegerProperty sortNum = new SimpleIntegerProperty();
    private ObjectProperty<EnumAlign> align = new SimpleObjectProperty<>(EnumAlign.LEFT);
    private ObjectProperty<DisplayStyle> displayStyle = new SimpleObjectProperty<>(DisplayStyle.TEXT_FIELD);
    private ObjectProperty<DictCategory> dict = new SimpleObjectProperty<>();
    private ObjectProperty<MetaDataType> dataType = new SimpleObjectProperty<>(MetaDataType.STRING);

    private TableModel tableModel;
    private Callback callback;

    public TableFieldModel() {
    }

    public TableFieldModel(String name, String displayName) {
        setName(name);
        setDisplayName(displayName);
    }

    public TableFieldModel(String name, String displayName, DisplayStyle displayStyle) {
        setName(name);
        setDisplayName(displayName);
        setDisplayStyle(displayStyle);
    }

    public TableFieldModel(String name, String displayName, MetaDataType dataType) {
        setName(name);
        setDisplayName(displayName);
        setDataType(dataType);
    }

    public TableFieldModel(String name, String displayName, DictCategory dict) {
        setName(name);
        setDisplayName(displayName);
        setDict(dict);
        setDisplayStyle(DisplayStyle.COMBO_BOX);
    }

    public TableFieldModel(String name, String displayName, int width, int sortNum) {
        setName(name);
        setDisplayName(displayName);
        setWidth(width);
        setSortNum(sortNum);
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

    public boolean isDisplay() {
        return isDisplay.get();
    }

    public BooleanProperty displayProperty() {
        return isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay.set(isDisplay);
    }

    public int getWidth() {
        return width.get();
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public void setWidth(int width) {
        this.width.set(width);
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

    public DisplayStyle getDisplayStyle() {
        return displayStyle.get();
    }

    public ObjectProperty<DisplayStyle> displayStyleProperty() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle.set(displayStyle);
    }

    public DictCategory getDict() {
        return dict.get();
    }

    public ObjectProperty<DictCategory> dictProperty() {
        return dict;
    }

    public void setDict(DictCategory dict) {
        this.dict.set(dict);
    }

    public MetaDataType getDataType() {
        return dataType.get();
    }

    public ObjectProperty<MetaDataType> dataTypeProperty() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType.set(dataType);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public boolean isPk() {
        return isPk.get();
    }

    public BooleanProperty pkProperty() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk.set(isPk);
    }

    public boolean isFk() {
        return isFk.get();
    }

    public BooleanProperty fkProperty() {
        return isFk;
    }

    public void setFk(boolean isFk) {
        this.isFk.set(isFk);
    }

    public boolean getEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable.set(editable);
    }

    public EnumAlign getAlign() {
        return align.get();
    }

    public ObjectProperty<EnumAlign> alignProperty() {
        return align;
    }

    public void setAlign(EnumAlign align) {
        this.align.set(align);
    }

    public static TableFieldModelBuilder builder() {
        return TableFieldModelBuilder.create();
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
}
