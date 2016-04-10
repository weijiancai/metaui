package com.metaui.eshop.api.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 参数信息
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "type", "require", "desc", "example", "category", "scope"})
public class ParamInfo {
    /**
     * 参数唯一标识
     */
    private String id;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数类型
     */
    private String type;
    /**
     * 是否必须
     */
    private boolean isRequire;
    /**
     * 参数描述
     */
    private String desc;
    /**
     * 示例
     */
    private String example;
    /**
     * 参数类别
     */
    private ParamCategory category;
    /**
     * 参数范围
     */
    private ParamScope scope;

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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    public boolean isRequire() {
        return isRequire;
    }

    public void setRequire(boolean require) {
        isRequire = require;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @XmlAttribute
    public ParamCategory getCategory() {
        return category;
    }

    public void setCategory(ParamCategory category) {
        this.category = category;
    }

    @XmlAttribute
    public ParamScope getScope() {
        return scope;
    }

    public void setScope(ParamScope scope) {
        this.scope = scope;
    }
}
