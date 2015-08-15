package com.metaui.fxbase.model;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import javafx.beans.property.*;

/**
 * 表单字段模型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldModel {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty displayName = new SimpleStringProperty();
    private BooleanProperty isDisplay = new SimpleBooleanProperty(true);
    private BooleanProperty isRequire = new SimpleBooleanProperty();
    private IntegerProperty width = new SimpleIntegerProperty(180);
    private IntegerProperty height = new SimpleIntegerProperty();
    private IntegerProperty maxLength = new SimpleIntegerProperty();
    private IntegerProperty sortNum = new SimpleIntegerProperty();
    private BooleanProperty isSingleLine = new SimpleBooleanProperty();
    private StringProperty defaultValue = new SimpleStringProperty();
    private StringProperty value = new SimpleStringProperty();
    private ObjectProperty<DisplayStyle> displayStyle = new SimpleObjectProperty<>(DisplayStyle.TEXT_FIELD);
    private ObjectProperty<DictCategory> dict = new SimpleObjectProperty<>();
    private ObjectProperty<QueryModel> queryModel = new SimpleObjectProperty<>(QueryModel.EQUAL);
    private ObjectProperty<MetaDataType> dataType = new SimpleObjectProperty<>(MetaDataType.STRING);

    public FormFieldModel() {
    }

    public FormFieldModel(String name, String displayName) {
        setName(name);
        setDisplayName(displayName);
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

    public boolean isRequire() {
        return isRequire.get();
    }

    public BooleanProperty requireProperty() {
        return isRequire;
    }

    public void setRequire(boolean isRequire) {
        this.isRequire.set(isRequire);
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

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    public boolean isSingleLine() {
        return isSingleLine.get();
    }

    public BooleanProperty singleLineProperty() {
        return isSingleLine;
    }

    public void setSingleLine(boolean isSingleLine) {
        this.isSingleLine.set(isSingleLine);
    }

    public int getMaxLength() {
        return maxLength.get();
    }

    public IntegerProperty maxLengthProperty() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength.set(maxLength);
    }

    public String getDefaultValue() {
        return defaultValue.get();
    }

    public StringProperty defaultValueProperty() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue.set(defaultValue);
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

    public QueryModel getQueryModel() {
        return queryModel.get();
    }

    public ObjectProperty<QueryModel> queryModelProperty() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel.set(queryModel);
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

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public static FormFieldBuilder builder() {
        return FormFieldBuilder.create();
    }
}
