package com.metaui.eshop.api.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Api分类
 *
 * @author wei_jc
 * @since 1.0
 */
@XmlRootElement
@XmlType(propOrder = {"name", "desc", "apiInfos"})
public class Category {
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 接口信息列表
     */
    private List<ApiInfo> apiInfos;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlElement(name = "api")
    public List<ApiInfo> getApiInfos() {
        return apiInfos;
    }

    public void setApiInfos(List<ApiInfo> apiInfos) {
        this.apiInfos = apiInfos;
    }

    @Override
    public String toString() {
        return name;
    }
}
