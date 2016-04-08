package com.metaui.eshop.api;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Api分类
 *
 * @author wei_jc
 * @since 1.0
 */
@XmlRootElement
public class ApiCategory {
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;

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
}
