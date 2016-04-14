package com.metaui.core.util.format;

/**
 * 代码美化接口
 *
 * @author wei_jc
 * @since 1.0
 */
public interface CodeFormat {
    /**
     * 代码美化
     *
     * @param code 要美化的代码
     * @return 返回美化后的代码
     * @throws Exception
     */
    String format(String code) throws Exception;
}
