package com.metaui.core.ui.layout;

import com.metaui.core.ui.model.LayoutProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 布局属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class LayoutProperties {
    public static List<LayoutProperty> getAllProperties() {
        List<LayoutProperty> list = new ArrayList<LayoutProperty>();
        list.addAll(getFormProperties());
        list.addAll(getFormFieldProperties());
        list.addAll(getTableFieldProperties());
        list.addAll(getCrudProperties());
        return list;
    }

    public static List<LayoutProperty> getFormFieldProperties() {
        List<LayoutProperty> list = new ArrayList<LayoutProperty>();
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.NAME, "名称", LayoutType.FORM_FIELD, null, PropertyType.IP, 10));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.DISPLAY_NAME, "显示名", LayoutType.FORM_FIELD, null, PropertyType.IP, 20));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.IS_SINGLE_LINE, "独行", LayoutType.FORM_FIELD, "false", PropertyType.IP, 30));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.IS_DISPLAY, "显示", LayoutType.FORM_FIELD, "true", PropertyType.IP, 40));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.IS_REQUIRE, "必须", LayoutType.FORM_FIELD, "false", PropertyType.IP, 50));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.IS_READONLY, "只读", LayoutType.FORM_FIELD, "false", PropertyType.IP, 55));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.WIDTH, "宽", LayoutType.FORM_FIELD, "180", PropertyType.IP, 60));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.HEIGHT, "高", LayoutType.FORM_FIELD, null, PropertyType.IP, 70));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.DISPLAY_STYLE, "显示风格", LayoutType.FORM_FIELD, "STRING", PropertyType.IP, 80));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.SORT_NUM, "排序号", LayoutType.FORM_FIELD, null, PropertyType.IP, 90));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.VALUE, "默认值", LayoutType.FORM_FIELD, null, PropertyType.IP, 100));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.DICT_ID, "数据字典", LayoutType.FORM_FIELD, null, PropertyType.IP, 110));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.QUERY_MODEL, "查询模式", LayoutType.FORM_FIELD, null, PropertyType.IP, 120));
        list.add(new LayoutProperty(PropertyNames.FORM_FIELD.MAX_LENGTH, "最大长度", LayoutType.FORM_FIELD, null, PropertyType.IP, 130));
        return list;
    }

    public static List<LayoutProperty> getFormProperties() {
        List<LayoutProperty> list = new ArrayList<LayoutProperty>();
        list.add(new LayoutProperty(PropertyNames.FORM.NAME, "名称", LayoutType.FORM, null, PropertyType.MP, 10));
        list.add(new LayoutProperty(PropertyNames.FORM.DISPLAY_NAME, "显示名", LayoutType.FORM, null, PropertyType.MP, 20));
        list.add(new LayoutProperty(PropertyNames.FORM.FORM_TYPE, "表单类型", LayoutType.FORM, null, PropertyType.MP, 30));
        list.add(new LayoutProperty(PropertyNames.FORM.COL_COUNT, "列数", LayoutType.FORM, "3", PropertyType.MP, 40));
        list.add(new LayoutProperty(PropertyNames.FORM.COL_WIDTH, "列宽", LayoutType.FORM, "180", PropertyType.MP, 50));
        list.add(new LayoutProperty(PropertyNames.FORM.LABEL_GAP, "labelGap", LayoutType.FORM, "5", PropertyType.MP, 60));
        list.add(new LayoutProperty(PropertyNames.FORM.FIELD_GAP, "fieldGap", LayoutType.FORM, "15", PropertyType.MP, 70));
        list.add(new LayoutProperty(PropertyNames.FORM.HGAP, "hgap", LayoutType.FORM, "3", PropertyType.MP, 80));
        list.add(new LayoutProperty(PropertyNames.FORM.VGAP, "vgap", LayoutType.FORM, "5", PropertyType.MP, 90));
        return list;
    }

    public static List<LayoutProperty> getTableFieldProperties() {
        List<LayoutProperty> list = new ArrayList<LayoutProperty>();
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.NAME, "名称", LayoutType.TABLE_FIELD, null, PropertyType.IP, 10));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.DISPLAY_NAME, "显示名", LayoutType.TABLE_FIELD, null, PropertyType.IP, 20));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.IS_DISPLAY, "显示", LayoutType.TABLE_FIELD, "true", PropertyType.IP, 30));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.WIDTH, "宽", LayoutType.TABLE_FIELD, "180", PropertyType.IP, 40));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.DISPLAY_STYLE, "显示风格", LayoutType.TABLE_FIELD, "STRING", PropertyType.IP, 50));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.SORT_NUM, "排序号", LayoutType.TABLE_FIELD, null, PropertyType.IP, 60));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.ALIGN, "对齐", LayoutType.TABLE_FIELD, null, PropertyType.IP, 70));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.DICT_ID, "数据字典", LayoutType.TABLE_FIELD, null, PropertyType.IP, 80));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.IS_PK, "主键", LayoutType.TABLE_FIELD, "false", PropertyType.IP, 90));
        list.add(new LayoutProperty(PropertyNames.TABLE_FIELD.IS_FK, "外键", LayoutType.TABLE_FIELD, "false", PropertyType.IP, 100));
        return list;
    }

    public static List<LayoutProperty> getCrudProperties() {
        List<LayoutProperty> list = new ArrayList<LayoutProperty>();
        list.add(new LayoutProperty(PropertyNames.CRUD.FORM, "表单", LayoutType.CRUD, null, PropertyType.MP, 10));
        list.add(new LayoutProperty(PropertyNames.CRUD.TABLE, "表格", LayoutType.CRUD, null, PropertyType.MP, 20));
        list.add(new LayoutProperty(PropertyNames.CRUD.QUERY, "查询", LayoutType.CRUD, null, PropertyType.MP, 30));
        return list;
    }
}
