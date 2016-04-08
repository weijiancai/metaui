package com.metaui.eshop.api;

import java.util.List;

/**
 * Api解析器，从网页内容中，获取api信息
 *
 * @author wei_jc
 * @since 1.0
 */
public interface ApiParser {
    /**
     * 解析网页Api
     *
     * @return 返回Api分类信息
     */
    List<ApiCategory> parse() throws Exception;
}
