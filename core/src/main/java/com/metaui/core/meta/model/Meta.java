package com.metaui.core.meta.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.metaui.core.datasource.DataMap;
import com.metaui.core.datasource.DataSource;
import com.metaui.core.datasource.QueryBuilder;
import com.metaui.core.datasource.VirtualResource;
import com.metaui.core.datasource.db.QueryResult;
import com.metaui.core.datasource.persist.IPDB;
import com.metaui.core.exception.MessageException;
import com.metaui.core.meta.MetaDataType;
import com.metaui.core.meta.MetaListener;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.meta.action.MUAction;
import com.metaui.core.meta.annotation.MetaElement;
import com.metaui.core.meta.annotation.MetaFieldElement;
import com.metaui.core.ui.ICanQuery;
import com.metaui.core.ui.IValue;
import com.metaui.core.ui.ViewManager;
import com.metaui.core.ui.model.View;
import com.metaui.core.util.UObject;
import com.metaui.core.util.UString;
import com.metaui.core.util.UUIDUtil;
import com.metaui.core.util.ftl.FreeMarkerConfiguration;
import com.metaui.core.util.ftl.FreeMarkerTemplateUtils;


import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.PrintWriter;
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
    private DataSource dataSource;
    private VirtualResource resource;
    private List<DataMap> dataList;

    private int totalRows; // 总行数
    private int pageCount; // 总页数
    private int pageRows = 15; // 每页行数

    private List<DataMap> insertCache = new ArrayList<DataMap>(); // 新增缓存
    private List<MUAction> actionList = new ArrayList<MUAction>(); // Action List

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

    @XmlTransient
    @JSONField(serialize = false)
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @XmlTransient
    public VirtualResource getResource() {
        return resource;
    }

    public void setResource(VirtualResource resource) {
        this.resource = resource;
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

    @XmlTransient
    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    @XmlTransient
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @XmlTransient
    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
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

    public QueryResult<DataMap> query(List<ICanQuery> queryList, int page, int rows) throws Exception {
        QueryResult<DataMap> result = resource.retrieve(this, queryList, page, rows);
        setDataList(result.getRows());
        setTotalRows(result.getTotal());
        setPageCount(result.getPageCount());
        return result;
    }

    /**
     * 删除行数据
     *
     * @param row 行号
     * @throws Exception
     */
    public void delete(int row) throws Exception {
        if(row < 0) {
            throw new MessageException("请选择要删除的行数据！");
        }
        DataMap rowData = getRowData(row);
        List<MetaField> pkFields = getPkFields();
        String[] values = new String[pkFields.size()];
        for (int i = 0; i < pkFields.size(); i++) {
            MetaField field = pkFields.get(i);
            values[i] = rowData.getString(field.getName());
        }
        resource.delete(this, values);
        dataList.remove(row);
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
    public View getFormView() {
        return ViewManager.getViewByName(getName() + "FormView");
    }

    @XmlTransient
    @JSONField(serialize = false)
    public View getTableView() {
        return ViewManager.getViewByName(getName() + "TableView");
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
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meta meta = (Meta) o;

        return id.equals(meta.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public QueryResult<DataMap> query(QueryBuilder builder) throws Exception {
        builder.sql().setQuerySql(sqlText);
        QueryResult<DataMap> result = resource.retrieve(builder, -1, 0);
        setDataList(result.getRows());
        setTotalRows(result.getTotal());
        setPageCount(result.getPageCount());
        return result;
    }

    public DataMap queryByPK(String... pkValues) throws Exception {
        QueryBuilder builder = QueryBuilder.create(this);
        builder.sql().setQuerySql(sqlText);
        int i = 0;
        for (MetaField field : getPkFields()) {
            builder.add(field.getOriginalName(), pkValues[i++]);
        }
        QueryResult<DataMap> result = resource.retrieve(builder, -1, 0);
        if (result.getTotal() == 0) {
            StringBuilder sb = new StringBuilder();
            for (String pkValue : pkValues) {
                sb.append(pkValue).append(",");
            }
            throw new RuntimeException(String.format("没有此主键值【%s】", sb.toString()));
        }
        if (result.getTotal() > 1) {
            throw new RuntimeException(String.format("根据主键检索，检索出多条数据！"));
        }
        return result.getRows().get(0);
    }

    public void deleteByPK(String... pkValues) throws Exception {
        QueryBuilder builder = QueryBuilder.create(this);
        builder.sql().setQuerySql(sqlText);
        int i = 0;
        for (MetaField field : getPkFields()) {
            builder.add(field.getOriginalName(), pkValues[i++]);
        }
        resource.delete(this, pkValues);
    }

    public void toTxtFile(File file, DataMap param) throws Exception {
        query(QueryBuilder.create(this));
        List<DataMap> dataList = getDataList();
        if (dataList == null) {
            return;
        }
        String[] colNames = param.getString("colNames").split(",");
        PrintWriter pw = new PrintWriter(file);
        for (DataMap map : dataList) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(Arrays.binarySearch(colNames, entry.getKey()) < 0) {
                    continue;
                }
                pw.print(UObject.toString(entry.getValue()));
                if (colNames.length > 1) {
                    pw.print("\t");
                }
            }
            pw.println();
        }
        pw.flush();
        pw.close();
    }

    public DataMap save(Map<String, Object> valueMap) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        for (String key : valueMap.keySet()) {
            MetaField field = fieldMap.get(key);

            if (field != null && UString.isNotEmpty(field.getOriginalName())) {
                // 其他值
                paramMap.put(field.getOriginalName(), valueMap.get(key));
            }
        }

        // 设置默认值
        for (MetaField field : getFields()) {
            if(!valueMap.containsKey(field.getName())) {
                String originalName = field.getOriginalName();
                // 设置默认值
                if (field.isPk() && !valueMap.containsKey(field.getName()) && field.getDataType() == MetaDataType.GUID) {
                    paramMap.put(originalName, UUIDUtil.getUUID());
                } else if (field.isRequire()) {
                    String defaultValue = field.getDefaultValue();
                    if (UString.isNotEmpty(defaultValue)) {
                        if ("GUID()".equals(defaultValue)) {
                            String guid = UUIDUtil.getUUID();
                            paramMap.put(originalName, guid);
                            valueMap.put(field.getName(), guid);
                        } else if ("SYSDATE()".equals(defaultValue)) {
                            Date date = new Date();
                            paramMap.put(originalName, date);
                            valueMap.put(field.getName(), date);
                        } else {
                            paramMap.put(originalName, defaultValue);
                        }
                    } else if (MetaDataType.BOOLEAN == field.getDataType()) {
                        paramMap.put(originalName, "T");
                    }
                }
            }
        }

        // 通知新增前监听
        for (MetaListener listener : MetaManager.getListeners()) {
            listener.addPrep(this, new DataMap(valueMap));
        }

        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
        map.put(resource.getName(), paramMap);

        resource.save(map);
        DataMap dataMap = new DataMap();
        dataMap.putAll(valueMap);
        getDataList().add(dataMap);

        // 通知新增后监听
        for (MetaListener listener : MetaManager.getListeners()) {
            listener.addEnd(this, dataMap);
        }

        return dataMap;
    }
    
    public void insertRow(DataMap dataMap) {
        insertCache.add(dataMap);
    }
    
    public void save() throws Exception {
        for (final DataMap dataMap : insertCache) {
            dataSource.save(new IPDB() {
                @Override
                public Map<String, Map<String, Object>> getPDBMap() {
                    Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                    map.put(resource.getName(), dataMap);
                    return map;
                }
            });
        }

        insertCache.clear();
    }

    public MetaField getFieldByName(String fieldName) {
        for (MetaField field : fields) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public void addAction(MUAction action) {
        actionList.add(action);
    }

    public List<MUAction> getActionList() {
        return actionList;
    }

    public void update(Map<String, IValue> modifiedValueMap, DataMap rowData) throws Exception {
        if (modifiedValueMap.size() == 0) {
            return;
        }

        Map<String, Object> valueMap = new HashMap<String, Object>();
        for (IValue value : modifiedValueMap.values()) {
            valueMap.put(value.getMetaField().getOriginalName(), value.value());
        }

        Map<String, Object> conditionMap = new HashMap<String, Object>();
        for (MetaField field : getFields()) {
            if (field.isPk()) {
                conditionMap.put(field.getOriginalName(), rowData.get(field.getName()));
            }
        }

        resource.update(valueMap, conditionMap, resource.getName());
        // 更新之后，更新原始DataMap值
        for (IValue value : modifiedValueMap.values()) {
            rowData.put(value.getName(), value.value());
        }
    }

    public String genJavaBeanCode() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("meta", this);
            return FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("JavaBean.ftl"), map);
        } catch (Exception e) {
            throw new RuntimeException("生成JavaBean代码失败！", e);
        }
    }

    public String genRowMapperCode() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("meta", this);
            return FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("rowMapper.ftl"), map);
        } catch (Exception e) {
            throw new RuntimeException("生成RowMapper代码失败！", e);
        }
    }

    public String getPDBCode() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("meta", this);
            return FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("PDBMapper.ftl"), map);
        } catch (Exception e) {
            throw new RuntimeException("生成PDB代码失败！", e);
        }
    }
}
