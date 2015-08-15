package com.metaui.fxbase.model;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldBuilder {
    private FormFieldModel model;

    public FormFieldBuilder(FormFieldModel model) {
        this.model = model;
    }

    public FormFieldBuilder name(String name) {
        model.setName(name);
        return this;
    }
    
    public FormFieldBuilder displayName(String displayName) {
        model.setDisplayName(displayName);
        return this;
    }

    public FormFieldBuilder display(boolean isDisplay) {
        model.setDisplay(isDisplay);
        return this;
    }

    public FormFieldBuilder require(boolean isRequire) {
        model.setRequire(isRequire);
        return this;
    }

    public FormFieldBuilder width(int width) {
        model.setWidth(width);
        return this;
    }

    public FormFieldBuilder height(int height) {
        model.setHeight(height);
        return this;
    }

    public FormFieldBuilder maxLength(int maxLength) {
        model.setMaxLength(maxLength);
        return this;
    }

    public FormFieldBuilder sortNum(int sortNum) {
        model.setSortNum(sortNum);
        return this;
    }

    public FormFieldBuilder singleLine(boolean isSingleLine) {
        model.setSingleLine(isSingleLine);
        return this;
    }

    public FormFieldBuilder defaultValue(String defaultValue) {
        model.setDefaultValue(defaultValue);
        return this;
    }

    public FormFieldBuilder value(String value) {
        model.setValue(value);
        return this;
    }

    public FormFieldBuilder displayStyle(DisplayStyle displayStyle) {
        model.setDisplayStyle(displayStyle);
        return this;
    }

    public FormFieldBuilder dict(DictCategory dict) {
        model.setDict(dict);
        return this;
    }

    public FormFieldBuilder queryModel(QueryModel queryModel) {
        model.setQueryModel(queryModel);
        return this;
    }

    public FormFieldBuilder dataType(MetaDataType dataType) {
        model.setDataType(dataType);
        return this;
    }

    public FormFieldModel build() {
        return model;
    }

    public static FormFieldBuilder create() {
        FormFieldModel model = new FormFieldModel();
        return new FormFieldBuilder(model);
    }
}
