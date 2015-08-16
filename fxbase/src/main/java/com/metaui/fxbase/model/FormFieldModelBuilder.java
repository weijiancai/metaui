package com.metaui.fxbase.model;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;

/**
 * 表单字段模型 Builder
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldModelBuilder {
    private FormFieldModel model;

    public FormFieldModelBuilder(FormFieldModel model) {
        this.model = model;
    }

    public FormFieldModelBuilder name(String name) {
        model.setName(name);
        return this;
    }
    
    public FormFieldModelBuilder displayName(String displayName) {
        model.setDisplayName(displayName);
        return this;
    }

    public FormFieldModelBuilder display(boolean isDisplay) {
        model.setDisplay(isDisplay);
        return this;
    }

    public FormFieldModelBuilder require(boolean isRequire) {
        model.setRequire(isRequire);
        return this;
    }

    public FormFieldModelBuilder width(int width) {
        model.setWidth(width);
        return this;
    }

    public FormFieldModelBuilder height(int height) {
        model.setHeight(height);
        return this;
    }

    public FormFieldModelBuilder maxLength(int maxLength) {
        model.setMaxLength(maxLength);
        return this;
    }

    public FormFieldModelBuilder sortNum(int sortNum) {
        model.setSortNum(sortNum);
        return this;
    }

    public FormFieldModelBuilder singleLine(boolean isSingleLine) {
        model.setSingleLine(isSingleLine);
        return this;
    }

    public FormFieldModelBuilder defaultValue(String defaultValue) {
        model.setDefaultValue(defaultValue);
        return this;
    }

    public FormFieldModelBuilder value(String value) {
        model.setValue(value);
        return this;
    }

    public FormFieldModelBuilder displayStyle(DisplayStyle displayStyle) {
        model.setDisplayStyle(displayStyle);
        return this;
    }

    public FormFieldModelBuilder dict(DictCategory dict) {
        model.setDict(dict);
        return this;
    }

    public FormFieldModelBuilder queryModel(QueryModel queryModel) {
        model.setQueryModel(queryModel);
        return this;
    }

    public FormFieldModelBuilder dataType(MetaDataType dataType) {
        model.setDataType(dataType);
        return this;
    }

    public FormFieldModelBuilder placeholder(String placeholder) {
        model.setPlaceholder(placeholder);
        return this;
    }

    public FormFieldModel build() {
        return model;
    }

    public static FormFieldModelBuilder create() {
        FormFieldModel model = new FormFieldModel();
        return new FormFieldModelBuilder(model);
    }
}
