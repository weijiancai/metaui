package com.metaui.core.ui.layout.property;

import com.metaui.core.dict.EnumAlign;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.meta.model.MetaReference;
import com.metaui.core.ui.layout.PropertyNames;
import com.metaui.core.ui.model.*;
import com.metaui.core.util.UNumber;
import com.metaui.core.util.UString;
import com.metaui.core.util.UUIDUtil;

import java.util.*;

/**
 * 表格属性配置
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableProperty implements PropertyNames {
    private View view;
    private List<TableFieldProperty> fieldProperties;
    private Map<MetaField, TableFieldProperty> fieldMap = new HashMap<MetaField, TableFieldProperty>();
    private Meta meta;

    public TableProperty() {
    }

    public TableProperty(View view) {
        this.view = view;
        this.meta = view.getMeta();
        fieldProperties = new ArrayList<TableFieldProperty>();
        for (MetaField field : meta.getFields()) {
            Map<String, ViewProperty> map = view.getMetaFieldConfig(field);
            TableFieldProperty fieldProperty = new TableFieldProperty(this, field, map);
            fieldProperties.add(fieldProperty);
            fieldMap.put(field, fieldProperty);
        }
        // 排序
        Collections.sort(fieldProperties, new Comparator<TableFieldProperty>() {
            @Override
            public int compare(TableFieldProperty o1, TableFieldProperty o2) {
                return o1.getSortNum() - o2.getSortNum();
            }
        });
    }

    public List<TableFieldProperty> getFieldProperties() {
        if (fieldProperties == null) {
            fieldProperties = new ArrayList<TableFieldProperty>();
        }
        return fieldProperties;
    }

    public void setFieldProperties(List<TableFieldProperty> fieldProperties) {
        this.fieldProperties = fieldProperties;
    }

    public void setPropertyValue(MetaField field, String propName, String propValue) {
        TableFieldProperty fieldProperty = fieldMap.get(field);
        if (fieldProperty != null) {
            if ("width".equalsIgnoreCase(propName)) {
                fieldProperty.setWidth(UNumber.toInt(propValue));
            } else if ("isDisplay".equalsIgnoreCase(propName)) {
                fieldProperty.setDisplay(UString.toBoolean(propValue));
            } else if ("displayName".equalsIgnoreCase(propName)) {
                fieldProperty.setDisplayName(propValue);
            } else if ("align".equalsIgnoreCase(propName)) {
                fieldProperty.setAlign(EnumAlign.getAlign(propValue));
            }
        }
    }

    public static View createTableView(Meta meta) {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "TableView");
        view.setDisplayName(meta.getDisplayName() + "表格视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        view.setViewProperties(createViewProperties(view, meta));

        return view;
    }

    public static List<ViewProperty> createViewProperties(View view, Meta meta) {
        List<ViewProperty> viewProperties = new ArrayList<ViewProperty>();
        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(TableFieldProperty.getViewProperties(view, field, true));
        }
        // 创建引用Meta属性配置
        for (MetaReference reference : meta.getReferences()) {
            for (MetaField field : reference.getPkMeta().getFields()) {
                viewProperties.addAll(TableFieldProperty.getViewProperties(view, field, false));
            }
        }
        return viewProperties;
    }

    public View getView() {
        return view;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
