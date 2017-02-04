package com.metaui.core.meta.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.metaui.core.datasource.DataMap;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.annotation.MetaElement;
import com.metaui.core.meta.annotation.MetaFieldElement;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * 元数据信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "displayName", "valid", "sortNum", "inputDate", "description", "fields"})
@MetaElement(displayName = "元数据")
public class Meta {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private String sqlText;
    private String rsId;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private List<MetaField> fields = new ArrayList<MetaField>();
    private List<MetaReference> references = new ArrayList<MetaReference>();
    private Set<Meta> children = new HashSet<Meta>();
    private Map<String, MetaField> fieldMap;
    private List<DataMap> dataList;

    public Meta() {}

    public Meta(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "ID", dataType = MetaDataType.NUMBER, sortNum = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "名称", sortNum = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "显示名", sortNum = 30)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "描述", sortNum = 40)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "Sql语句", sortNum = 45)
    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "资源ID", sortNum = 80, maxLength = 300)
    public String getRsId() {
        return rsId;
    }

    public void setRsId(String rsId) {
        this.rsId = rsId;
    }

    @XmlAttribute
    @MetaFieldElement(name = "valid", displayName = "是否有效", dataType=MetaDataType.BOOLEAN, dictId = "EnumBoolean", sortNum = 50)
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "排序号", sortNum = 60)
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "录入时间", sortNum = 70, dataType = MetaDataType.DATE)
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @XmlElement(name = "MetaField")
    public List<MetaField> getFields() {
        return fields;
    }

    public void setFields(List<MetaField> fields) {
        this.fields = fields;
        fieldMap = new HashMap<String, MetaField>();
        for (MetaField field : fields) {
            fieldMap.put(field.getName(), field);
        }
    }

    public void setFieldValue(String fieldName, String value) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setValue(value);
                break;
            }
        }
    }

    public String getFieldValue(String fieldName) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                return field.getValue();
            }
        }

        return null;
    }

    /**
     * 获得主键元数据字段列表
     *
     * @return 返回主键元字段列表
     */
    @JSONField(serialize = false)
    public List<MetaField> getPkFields() {
        List<MetaField> result = new ArrayList<MetaField>();
        for (MetaField field : fields) {
            if (field.isPk()) {
                result.add(field);
            }
        }
        return result;
    }

    /**
     * 根据原名称（例如数据库列名），获得元字段信息
     *
     * @param originalName 原名称（例如数据库列名）
     * @return 返回元字段信息
     */
    public MetaField getFieldByOriginalName(String originalName) {
        for (MetaField field : fields) {
            if (originalName.equalsIgnoreCase(field.getOriginalName())) {
                return field;
            }
        }

        return null;
    }

    @XmlTransient
    public List<DataMap> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataMap> list) {
        this.dataList = list;
    }

    /**
     * 获得行数据Map
     *
     * @param row 行号
     * @return 返回行数据Map
     */
    public DataMap getRowData(int row) {
        return dataList.get(row);
    }

    /**
     * 获得引用的元数据信息
     *
     * @param fieldId 元字段ID
     * @return 返回引用的元数据信息
     */
    public Meta getRefMeta(String fieldId) {
        for (MetaField field : fields) {
            if (field.getRefField() != null && field.getId().equals(fieldId)) {
                return field.getRefField().getMeta();
            }
        }

        return null;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public List<MetaReference> getReferences() {
        if (references == null) {
            references = new ArrayList<MetaReference>();
        }
        return references;
    }

    public void setReferences(List<MetaReference> references) {
        this.references = references;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public Set<Meta> getChildren() {
        return children;
    }

    public void setChildren(Set<Meta> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meta meta = (Meta) o;

        return id.equals(meta.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public MetaField getFieldByName(String fieldName) {
        for (MetaField field : fields) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        return null;
    }
}
