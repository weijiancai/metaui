package com.metaui.eshop.api.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 错误码
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "desc", "solution"})
public class ErrorInfo {
    /**
     * 错误码唯一标识
     */
    private String id;
    /**
     * 错误码名称
     */
    private String name;
    /**
     * 错误码描述
     */
    private String desc;
    /**
     * 解决方案
     */
    private String solution;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
