package com.metaui.core.config.preference;

import javax.xml.bind.annotation.*;

/**
 * 首选项
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"section", "key", "value"})
public class Preference {
    /**
     * 配置类型
     */
    private String section;
    /**
     * 属性名
     */
    private String key;
    /**
     * 属性值
     */
    private String value;

    public Preference() {
    }

    public Preference(String key, String value) {
        this.section = Preferences.ROOT;
        this.key = key;
        this.value = value;
    }

    public Preference(String section, String key, String value) {
        this.section = section;
        this.key = key;
        this.value = value;
    }

    @XmlAttribute
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @XmlAttribute
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
