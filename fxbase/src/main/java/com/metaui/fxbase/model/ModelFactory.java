package com.metaui.fxbase.model;

import com.metaui.core.dict.DictManager;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0
 */
public class ModelFactory {
    /**
     * 将元数据转换为表单模型
     *
     * @param meta
     * @param model
     * @return
     */
    public static void convert(Meta meta, FormModel model) {
        model.setName(meta.getName());
        model.setDisplayName(meta.getDisplayName());

        List<FormFieldModel> fieldModels = new ArrayList<>();

        for (MetaField field : meta.getFields()) {
            FormFieldModel fieldModel = FormFieldModel.builder()
                    .name(field.getName())
                    .displayName(field.getDisplayName())
                    .dataType(field.getDataType())
                    .display(field.isValid())
                    .defaultValue(field.getDefaultValue())
                    .value(field.getValue())
                    .dict(DictManager.getDict(field.getDictId()))
                    .require(field.isRequire())
                    .sortNum(field.getSortNum())
                    .build();

            fieldModels.add(fieldModel);
        }

        model.setFormFields(fieldModels);
    }
}
