package com.metaui.eshop.api.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 代码示例
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
@XmlRootElement
@XmlType(propOrder = {"language", "code"})
public class CodeExample {
    /**
     * 代码语言
     */
    private CodeLanguage language;
    /**
     * 代码
     */
    private String code;

    @XmlAttribute
    public CodeLanguage getLanguage() {
        return language;
    }

    public void setLanguage(CodeLanguage language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
