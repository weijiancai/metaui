package com.metaui.core.view.config;

import com.metaui.core.meta.DisplayStyle;

import java.util.Date;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2017/1/28.
 */
public class FormFieldConfig {
    private String id;   // ID
    private String name;  // 名称
    private String displayName;  // 显示名
    private boolean isDisplay = true;  // 是否显示
    private boolean isRequire;  // 是否必需
    private boolean isReadonly;  // 是否只读
    private boolean isSingleLine;  // 是否独行
    private int width;  // 宽
    private int height;  // 行间隔
    private String defaultValue;  // 默认值
    private String placeholder;  // 提示信息
    private DisplayStyle displayStyle = DisplayStyle.TEXT_FIELD;  // 显示风格
    private String dictId;  // 数据字典
    private boolean isValid = true;  // 是否有效
    private int sortNum;  // 排序号
    private Date inputDate;    // 录入时间
    private String metaFieldId;  // 元字段

    private FormConfig formConfig;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    public boolean isRequire() {
        return isRequire;
    }

    public void setRequire(boolean require) {
        isRequire = require;
    }

    public boolean isReadonly() {
        return isReadonly;
    }

    public void setReadonly(boolean readonly) {
        isReadonly = readonly;
    }

    public boolean isSingleLine() {
        return isSingleLine;
    }

    public void setSingleLine(boolean singleLine) {
        isSingleLine = singleLine;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public DisplayStyle getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getMetaFieldId() {
        return metaFieldId;
    }

    public void setMetaFieldId(String metaFieldId) {
        this.metaFieldId = metaFieldId;
    }

    public FormConfig getFormConfig() {
        return formConfig;
    }

    public void setFormConfig(FormConfig formConfig) {
        this.formConfig = formConfig;
    }
}
