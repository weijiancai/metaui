package com.metaui.fxbase.view.table.model;

import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.MetaDataType;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TableFieldModelBuilder {
    private TableFieldModel model;

    private TableFieldModelBuilder(TableFieldModel model) {
        this.model = model;
    }

    public TableFieldModelBuilder name(String name) {
        model.setName(name);
        return this;
    }

    public TableFieldModelBuilder displayName(String displayName) {
        model.setDisplayName(displayName);
        return this;
    }

    public TableFieldModelBuilder width(int width) {
        model.setWidth(width);
        return this;
    }

    public TableFieldModelBuilder sortNum(int sortNum) {
        model.setSortNum(sortNum);
        return this;
    }

    public TableFieldModelBuilder display(boolean isDisplay) {
        model.setDisplay(isDisplay);
        return this;
    }

    public TableFieldModelBuilder pk(boolean isPk) {
        model.setPk(isPk);
        return this;
    }

    public TableFieldModelBuilder fk(boolean isFk) {
        model.setFk(isFk);
        return this;
    }

    public TableFieldModelBuilder editable(boolean editable) {
        model.setEditable(editable);
        return this;
    }

    public TableFieldModelBuilder align(EnumAlign align) {
        model.setAlign(align);
        return this;
    }

    public TableFieldModelBuilder displayStyle(DisplayStyle displayStyle) {
        model.setDisplayStyle(displayStyle);
        return this;
    }

    public TableFieldModelBuilder dict(DictCategory dictCategory) {
        model.setDict(dictCategory);
        if (dictCategory != null) {
            model.setDisplayStyle(DisplayStyle.COMBO_BOX);
        }
        return this;
    }

    public TableFieldModelBuilder dataType(MetaDataType dataType) {
        model.setDataType(dataType);
        return this;
    }

    public TableFieldModel build() {
        return model;
    }

    public static TableFieldModelBuilder create() {
        TableFieldModel model = new TableFieldModel();
        return new TableFieldModelBuilder(model);
    }
}
