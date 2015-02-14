package com.metaui.core.ui.config.layout;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictManager;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.ILayoutConfig;
import com.metaui.core.util.UString;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 表单字段配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldConfig extends BaseLayoutConfig<FormFieldConfig> {
    private MetaField metaField;
    private FormConfig formConfig;
    private StringProperty valueProperty = new SimpleStringProperty();

    public FormFieldConfig(FormConfig formConfig, ILayoutConfig config) {
        super(config);
        this.formConfig = formConfig;
    }

    public String getName() {
        return getStringValue(FORM_FIELD_NAME);
    }

    public void setName(String name) {
        setValue(FORM_FIELD_NAME, name);
    }

    public String getDisplayName() {
        return getStringValue(FORM_FIELD_DISPLAY_NAME);
    }

    public void setDisplayName(String displayName) {
        setValue(FORM_FIELD_DISPLAY_NAME, displayName);
    }

    public boolean isSingleLine() {
        return getBooleanValue(FORM_FIELD_IS_SINGLE_LINE);
    }

    public void setSingleLine(boolean singleLine) {
        setValue(FORM_FIELD_IS_SINGLE_LINE, singleLine + "");
    }

    public boolean isDisplay() {
        return getBooleanValue(FORM_FIELD_IS_DISPLAY);
    }

    public void setDisplay(boolean display) {
        setValue(FORM_FIELD_IS_DISPLAY, isDisplay() + "");
    }

    public DictCategory getDict() {
        String dictId = getStringValue(FORM_FIELD_DICT_ID);
        if (UString.isNotEmpty(dictId)) {
            return DictManager.getDict(dictId);
        }

        return null;
    }

    public void setDict(String dictId) {
        setValue(FORM_FIELD_DICT_ID, dictId);
    }

    /*@XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }*/

    public int getWidth() {
        int width = getIntValue(FORM_FIELD_WIDTH);
        return width == 0 ? getFormConfig().getColWidth() : width;
    }

    public void setWidth(int width) {
        setValue(FORM_FIELD_WIDTH, width + "");
    }

    public int getHeight() {
        return getIntValue(FORM_FIELD_HEIGHT);
    }

    public void setHeight(int height) {
        setValue(FORM_FIELD_HEIGHT, height + "");
    }

    public DisplayStyle getDisplayStyle() {
        return DisplayStyle.getStyle(getStringValue(FORM_FIELD_DISPLAY_STYLE));
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        setValue(FORM_FIELD_DISPLAY_STYLE, displayStyle.name());
    }

    /*@XmlAttribute
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }*/

    public int getSortNum() {
        return getIntValue(FORM_FIELD_SORT_NUM);
    }

    public void setSortNum(int sortNum) {
        setValue(FORM_FIELD_SORT_NUM, sortNum + "");
    }

    public MetaDataType getDataType() {
        return MetaDataType.getDataType(getStringValue(FORM_FIELD_DATA_TYPE));
    }

    public void setDataType(MetaDataType dataType) {
        setValue(FORM_FIELD_DATA_TYPE, dataType.ordinal() + "");
    }

    public String getValue() {
        return getStringValue(FORM_FIELD_VALUE);
    }

    public void setValue(String value) {
        setValue(FORM_FIELD_VALUE, value);
        valueProperty.set(value);
    }

    public StringProperty valueProperty() {
        return valueProperty;
    }

    /*@XmlTransient
    public MetaForm getForm() {
        return form;
    }

    public void setForm(MetaForm form) {
        this.form = form;
    }*/

    public FormConfig getFormConfig() {
        return formConfig;
    }

    public void setFormConfig(FormConfig formConfig) {
        this.formConfig = formConfig;
    }

    public MetaField getMetaField() {
        return metaField;
    }

    public void setMetaField(MetaField metaField) {
        this.metaField = metaField;
    }

    @Override
    public int compareTo(FormFieldConfig o) {
        return this.getSortNum() - o.getSortNum();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
