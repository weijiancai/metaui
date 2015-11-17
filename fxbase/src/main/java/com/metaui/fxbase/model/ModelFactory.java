package com.metaui.fxbase.model;

import com.metaui.core.R;
import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.db.object.DBColumn;
import com.metaui.core.datasource.db.object.DBDataset;
import com.metaui.core.dict.DictManager;
import com.metaui.core.dict.EnumAlign;
import com.metaui.core.dict.FormType;
import com.metaui.core.dict.QueryModel;
import com.metaui.core.meta.DisplayStyle;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.layout.PropertyNames;
import com.metaui.core.ui.model.View;
import com.metaui.core.ui.model.ViewProperty;
import com.metaui.core.util.UClass;
import com.metaui.fxbase.view.dialog.MUDialog;
import com.metaui.fxbase.view.table.model.TableFieldModel;
import com.metaui.fxbase.view.table.model.TableModel;
import javafx.collections.FXCollections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    .formModel(model)
                    .name(field.getName())
                    .displayName(field.getDisplayName())
                    .queryName(field.getOriginalName())
                    .queryModel(QueryModel.EQUAL)
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

    /**
     * 将表单View转换为表单模型
     *
     * @param view
     * @param model
     * @return
     */
    public static void convert(View view, FormModel model) {
        model.setName(view.getName());
        model.setDisplayName(view.getDisplayName());
        model.setFormType(FormType.convert(view.getProperty(PropertyNames.FORM.FORM_TYPE).getValue()));

        List<FormFieldModel> fieldModels = new ArrayList<>();

        for (MetaField field : view.getMetaFieldList()) {
            FormFieldModel fieldModel = FormFieldModel.builder()
                    .formModel(model)
                    .name(view.getStringProperty(field, PropertyNames.FORM_FIELD.NAME))
                    .displayName(view.getStringProperty(field, PropertyNames.FORM_FIELD.DISPLAY_NAME))
                    .queryName(field.getOriginalName())
                    .display(view.getBooleanProperty(field, PropertyNames.FORM_FIELD.IS_DISPLAY))
                    .displayStyle(DisplayStyle.getStyle(view.getStringProperty(field, PropertyNames.FORM_FIELD.DISPLAY_STYLE)))
                    .value(view.getStringProperty(field, PropertyNames.FORM_FIELD.VALUE))
                    .defaultValue(field.getDefaultValue())
                    .height(view.getIntProperty(field, PropertyNames.FORM_FIELD.HEIGHT))
                    .width(view.getIntProperty(field, PropertyNames.FORM_FIELD.WIDTH))
                    .dict(DictManager.getDict(view.getStringProperty(field, PropertyNames.FORM_FIELD.DICT_ID)))
                    .sortNum(view.getIntProperty(field, PropertyNames.FORM_FIELD.SORT_NUM))
                    .readonly(view.getBooleanProperty(field, PropertyNames.FORM_FIELD.IS_READONLY))
                    .singleLine(view.getBooleanProperty(field, PropertyNames.FORM_FIELD.IS_SINGLE_LINE))
                    .maxLength(view.getIntProperty(field, PropertyNames.FORM_FIELD.MAX_LENGTH))
                    .dataType(field.getDataType())
                    .build();

            fieldModels.add(fieldModel);
        }

        model.setFormFields(fieldModels);
    }

    /**
     * 将元数据转换为表格模型
     *
     * @param meta
     * @param model
     * @return
     */
    public static void convert(Meta meta, TableModel model) {
        List<TableFieldModel> fieldModels = new ArrayList<>();

        for (MetaField field : meta.getFields()) {
            TableFieldModel fieldModel = TableFieldModel.builder()
                    .name(field.getName())
                    .displayName(field.getDisplayName())
                    .display(field.isValid())
                    .dict(DictManager.getDict(field.getDictId()))
                    .sortNum(field.getSortNum())
                    .pk(field.isPk())
                    .fk(field.isFk())
                    .dataType(field.getDataType())
                    .build();

            fieldModels.add(fieldModel);
        }
        model.setTableFields(FXCollections.observableArrayList(fieldModels));
    }

    /**
     * 将数据库表、视图等转换为表格模型
     *
     * @param dataset
     * @param model
     * @return
     */
    public static void convert(DBDataset dataset, TableModel model) {
        List<TableFieldModel> fieldModels = new ArrayList<>();

        try {
            for (DBColumn column : dataset.getColumns()) {
                TableFieldModel fieldModel = TableFieldModel.builder()
                        .name(column.getName())
                        .displayName(column.getDisplayName())
                        .sortNum(column.getSortNum())
                        .pk(column.isPk())
                        .fk(column.isFk())
                        .dataType(column.getDataType())
                        .build();

                fieldModels.add(fieldModel);
            }
        } catch (Exception e) {
            MUDialog.showException(e);
        }
        model.setTableFields(FXCollections.observableArrayList(fieldModels));
    }

    public static void convert(View view, TableModel model) {
        List<TableFieldModel> fieldModels = new ArrayList<>();

        for (MetaField field : view.getMetaFieldList()) {
            TableFieldModel fieldModel = TableFieldModel.builder()
                    .name(view.getStringProperty(field, PropertyNames.TABLE_FIELD.NAME))
                    .displayName(view.getStringProperty(field, PropertyNames.TABLE_FIELD.DISPLAY_NAME))
                    .display(view.getBooleanProperty(field, PropertyNames.TABLE_FIELD.IS_DISPLAY))
                    .displayStyle(DisplayStyle.getStyle(view.getStringProperty(field, PropertyNames.TABLE_FIELD.DISPLAY_STYLE)))
                    .align(EnumAlign.getAlign(view.getStringProperty(field, PropertyNames.TABLE_FIELD.DISPLAY_STYLE)))
                    .dict(DictManager.getDict(view.getStringProperty(field, PropertyNames.TABLE_FIELD.DICT_ID)))
                    .width(view.getIntProperty(field, PropertyNames.TABLE_FIELD.WIDTH))
                    .sortNum(view.getIntProperty(field, PropertyNames.TABLE_FIELD.SORT_NUM))
                    .pk(view.getBooleanProperty(field, PropertyNames.TABLE_FIELD.IS_PK))
                    .fk(view.getBooleanProperty(field, PropertyNames.TABLE_FIELD.IS_FK))
                    .dataType(field.getDataType())
                    .build();

            fieldModels.add(fieldModel);
        }
        model.setTableFields(FXCollections.observableArrayList(fieldModels));
    }

    public static void convert(List data, TableModel model, TableFieldModel... fields) throws Exception {
        List<TableFieldModel> fieldModels = new ArrayList<>();
        List<DataMap> values = new ArrayList<>();

        for (TableFieldModel fieldModel : fields) {
            fieldModels.add(fieldModel);
        }

        for (Object obj : data) {
            DataMap dataMap = new DataMap();
            Class<?> clazz = obj.getClass();
            for (TableFieldModel fieldModel : fields) {
                Method method = clazz.getMethod(UClass.getGetMethodName(fieldModel.getName()));
                dataMap.put(fieldModel.getName(), method == null ? null : method.invoke(obj));
            }
            values.add(dataMap);
        }

        model.setTableFields(FXCollections.observableArrayList(fieldModels));
        model.setValues(values);
    }
}
