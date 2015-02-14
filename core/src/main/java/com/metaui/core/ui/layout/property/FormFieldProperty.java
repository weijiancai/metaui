package com.metaui.core.ui.layout.property;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.FormType;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.layout.*;
import com.metaui.core.ui.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldProperty extends BaseProperty {
    private String name;
    private String displayName;
    private QueryModel queryModel = QueryModel.EQUAL;
    private boolean isSingleLine;
    private boolean isDisplay = true;
    private boolean isRequire;
    private int width = 180;
    private int height;
    private int maxLength;
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private MetaDataType dataType;
    private String defaultValue;
    private int sortNum;

    private FormProperty formProperty;

    public FormFieldProperty() {
    }

    public FormFieldProperty(FormProperty formProperty, MetaField field, Map<String, ViewProperty> propMap) {
        super(formProperty.getView(), field, propMap);
        this.formProperty = formProperty;

        // 初始化默认值
        int defaultWidth = 180;
        if(FormType.QUERY == formProperty.getFormType()) {
            defaultWidth = 250;
        }
        DisplayStyle defaultDisplayStyle = DisplayStyle.TEXT_FIELD;
        boolean defaultSingleLine = false;
        int defaultHeight = 0;
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            defaultDisplayStyle = DisplayStyle.BOOLEAN;
        } else if (MetaDataType.DICT == field.getDataType()) {
            defaultDisplayStyle = DisplayStyle.COMBO_BOX;
        } else if (MetaDataType.DATE == field.getDataType()) {
            defaultDisplayStyle = DisplayStyle.DATE;
        } else if (MetaDataType.TEXT == field.getDataType()) {
            defaultDisplayStyle = DisplayStyle.TEXT_AREA;
            defaultHeight = 500;
        }
        if (field.getMaxLength() > 500 && field.getDataType() != MetaDataType.TEXT) {
            defaultDisplayStyle = DisplayStyle.TEXT_AREA;
            defaultSingleLine = true;
            defaultHeight = 60;
        } else if(field.getMaxLength() >= 200) {
            defaultSingleLine = true;
        }

        dataType = field.getDataType();
        name = field.getName();
        maxLength = getIntPropertyValue(FORM_FIELD.MAX_LENGTH, field.getMaxLength());
        displayName = getPropertyValue(FORM_FIELD.DISPLAY_NAME, field.getDisplayName());
        queryModel = QueryModel.convert(getPropertyValue(FORM_FIELD.QUERY_MODEL, QueryModel.EQUAL.name()));
        isSingleLine = getBooleanPropertyValue(FORM_FIELD.IS_SINGLE_LINE, defaultSingleLine);
        isDisplay = getBooleanPropertyValue(FORM_FIELD.IS_DISPLAY, true);
        isRequire = getBooleanPropertyValue(FORM_FIELD.IS_REQUIRE, field.isRequire());
        width = getIntPropertyValue(FORM_FIELD.WIDTH, defaultWidth);
        height = getIntPropertyValue(FORM_FIELD.HEIGHT, defaultHeight);
        displayStyle = DisplayStyle.getStyle(getPropertyValue(FORM_FIELD.DISPLAY_STYLE, defaultDisplayStyle.name()));
        dict = DictManager.getDict(getPropertyValue(FORM_FIELD.DICT_ID, field.getDictId()));
        defaultValue = getPropertyValue(FORM_FIELD.VALUE, field.getDefaultValue());
        sortNum = getIntPropertyValue(FORM_FIELD.SORT_NUM, field.getSortNum());
    }

    public FormFieldProperty(String name, String displayName, int sortNum) {
        this(name, displayName, QueryModel.EQUAL, sortNum);
    }

    public FormFieldProperty(String name, String displayName, QueryModel queryModel, int sortNum) {
        this.name = name;
        this.displayName = displayName;
        this.queryModel = queryModel;
        this.sortNum = sortNum;
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
    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
        width = 250;
    }
    public boolean isSingleLine() {
        return isSingleLine;
    }

    public void setSingleLine(boolean isSingleLine) {
        this.isSingleLine = isSingleLine;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public boolean isRequire() {
        return isRequire;
    }

    public void setRequire(boolean isRequire) {
        this.isRequire = isRequire;
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

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public DisplayStyle getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
    }

    public DictCategory getDict() {
        return dict;
    }

    public void setDict(DictCategory dict) {
        this.dict = dict;
    }

    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public FormProperty getFormProperty() {
        return formProperty;
    }

    public void setFormProperty(FormProperty formProperty) {
        this.formProperty = formProperty;
    }

    public static List<ViewProperty> getViewProperties(View view, MetaField field, FormType formType) {
        List<ViewProperty> configList = new ArrayList<ViewProperty>();
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.NAME), field, field.getName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.DISPLAY_NAME), field, field.getDisplayName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.IS_DISPLAY), field, "true"));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.QUERY_MODEL), field, QueryModel.EQUAL.name()));

        String width = "180";
        String height = "";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String singleLine = "false";
        String dictId = field.getDictId();
        String defaultValue = field.getDefaultValue();
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            displayStyle = DisplayStyle.BOOLEAN.name();
            dictId = "EnumBoolean";
        } else if (MetaDataType.DICT == field.getDataType()) {
            displayStyle = DisplayStyle.COMBO_BOX.name();
        } else if (MetaDataType.DATE == field.getDataType()) {
            if(FormType.QUERY == formType) {
                width = "250";
            }
            displayStyle = DisplayStyle.DATE.name();
        } else if (MetaDataType.GUID == field.getDataType()) {
            defaultValue = "GUID()";
        } else if (MetaDataType.TEXT == field.getDataType()) {
            displayStyle = DisplayStyle.TEXT_AREA.name();
            height = "500";
        }

        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.IS_REQUIRE), field, field.isRequire() ? "true" : "false"));
        // 显示风格
        if (field.getMaxLength() > 500 && field.getDataType() != MetaDataType.TEXT) {
            displayStyle = DisplayStyle.TEXT_AREA.name();
            singleLine = "true";
            height = "60";
        } else if(field.getMaxLength() >= 200) {
            singleLine = "true";
        }

        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.IS_SINGLE_LINE), field, singleLine));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.WIDTH), field, width));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.HEIGHT), field, height));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.DISPLAY_STYLE), field, displayStyle));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.VALUE), field, defaultValue));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.DICT_ID), field, dictId));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.SORT_NUM), field, field.getSortNum() + ""));

        return configList;
    }

    public int getDefaultWidth() {
        int defaultWidth = 180;
        if(FormType.QUERY == formProperty.getFormType()) {
            width = 250;
        }
        return defaultWidth;
    }
}
