package com.metaui.core.ui.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.metaui.core.meta.model.Meta;
import com.metaui.core.meta.model.MetaField;
import com.metaui.core.ui.layout.property.FormProperty;
import com.metaui.core.util.UNumber;
import com.metaui.core.util.UString;
import javafx.scene.Node;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

/**
 * 视图
 *
 * @author wei_jc
 * @since  1.0.0
 */
@XmlRootElement
public class View {
    /** 视图ID */
    private String id;
    /** 视图名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 描述 */
    private String description;
    /** 是否有效 */
    private boolean isValid;
    /** 录入时间 */
    private Date inputDate;
    /** 排序号 */
    private int sortNum;
    /** 元数据ID */
    private String metaId;
    private Node node;

    /** 元字段属性Map */
    private Map<MetaField, Map<String, ViewProperty>> fieldPropMap = new HashMap<MetaField, Map<String, ViewProperty>>();

    private List<ViewLayout> layoutList;
    private List<ViewConfig> configs;
    private List<ViewProperty> viewProperties;

    private Map<String, ViewProperty> propMap = new HashMap<String, ViewProperty>();
    private Meta meta;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @XmlAttribute
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public List<ViewLayout> getLayoutList() {
        if (layoutList == null) {
            layoutList = new ArrayList<ViewLayout>();
        }
        return layoutList;
    }

    public void setLayoutList(List<ViewLayout> layoutList) {
        this.layoutList = layoutList;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public List<ViewConfig> getConfigs() {
        if (configs == null) {
            configs = new ArrayList<ViewConfig>();
        }
        return configs;
    }

    public void setConfigs(List<ViewConfig> configs) {
        this.configs = configs;
    }

    public void addConfig(ViewConfig config) {
        getConfigs().add(config);
    }

    @XmlElement(name = "ViewProperty")
    @JSONField(serialize = false)
    public List<ViewProperty> getViewProperties() {
        if (viewProperties == null) {
            viewProperties = new ArrayList<ViewProperty>();
        }
        return viewProperties;
    }

    public void setViewProperties(List<ViewProperty> viewProperties) {
        for (ViewProperty property : viewProperties) {
            addViewProperty(property);
        }
    }

    public void addViewProperty(ViewProperty property) {
        if (property.getProperty() == null) {
            return;
        }
        propMap.put(property.getProperty().getId(), property);
        MetaField field = property.getField();
        if (field != null) {
            Map<String, ViewProperty> propMap = fieldPropMap.get(field);
            if (propMap == null) {
                propMap = new HashMap<String, ViewProperty>();
                fieldPropMap.put(field, propMap);
            }
            propMap.put(property.getProperty().getId(), property);
        }

        getViewProperties().add(property);
    }

    public ViewProperty getProperty(String propertyId) {
        return propMap.get(propertyId);
    }

    public String getStringProperty(String propertyId) {
        return propMap.get(propertyId).getValue();
    }

    public String getStringProperty(String propertyId, String defaultValue) {
        ViewProperty viewProperty = getProperty(propertyId);
        if (viewProperty == null) {
            return defaultValue;
        }
        return viewProperty.getValue();
    }

    public int getIntProperty(String propertyId) {
        return UNumber.toInt(propMap.get(propertyId).getValue());
    }

    public int getIntProperty(String propertyId, int defaultValue) {
        ViewProperty viewProperty = getProperty(propertyId);
        if (viewProperty == null) {
            return defaultValue;
        }
        return UNumber.toInt(viewProperty.getValue());
    }

    public boolean getBooleanProperty(String propertyId) {
        return UString.toBoolean(propMap.get(propertyId).getValue());
    }

    /**
     * 根据元字段Id获得此元字段的布局配置信息
     *
     * @param field 元字段ID
     * @return 返回元字段配置
     */
    public Map<String, ViewProperty> getMetaFieldConfig(MetaField field) {
        Map<String, ViewProperty> result = fieldPropMap.get(field);
        return result == null ? new HashMap<String, ViewProperty>() : result;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public List<MetaField> getMetaFieldList() {
        return new ArrayList<MetaField>(fieldPropMap.keySet());
    }

    @XmlTransient
    @JSONField(serialize = false)
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ViewProperty getViewProperty(MetaField field, String propName) {
        for (ViewProperty property : viewProperties) {
            if (property.getField() == null || property.getProperty() == null) {
                continue;
            }

            if (property.getField() == field && property.getProperty().getName().equalsIgnoreCase(propName)) {
                return property;
            }
        }

        return null;
    }

    @XmlTransient
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public static View createNodeView(Node node) {
        View view = new View();
        view.setNode(node);
        return view;
    }

    @XmlTransient
    public FormProperty getQueryForm() {
        return new FormProperty(this);
    }
}
