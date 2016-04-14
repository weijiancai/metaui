package com.metaui.core.util.format;

/**
 * 代码美化工厂类
 *
 * @author wei_jc
 * @since 1.0
 */
public class CodeFormatFactory {
    /**
     * 美化JSON代码
     *
     * @param code JSON代码
     * @return
     * @throws Exception
     */
    public static String json(String code) throws Exception {
        return new JsonFormat().format(code);
    }

    /**
     * 美化XML代码
     *
     * @param code XML代码
     * @return
     * @throws Exception
     */
    public static String xml(String code) throws Exception {
        return new XmlFormat().format(code);
    }
}
