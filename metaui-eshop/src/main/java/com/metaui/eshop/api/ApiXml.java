package com.metaui.eshop.api;

import com.metaui.eshop.api.domain.Category;

import java.util.List;

/**
 * Api接口XML存储
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public interface ApiXml {
    /**
     * 获得Api分类列表
     *
     * @return 返回Api分类列表
     */
    List<Category> getCategories();

    /**
     * 设置Api分类列表
     *
     * @param categories Api分类列表
     */
    void setCategories(List<Category> categories);

    /**
     * 保存
     */
    void save() throws Exception;

    /**
     * 加载
     */
    void load() throws Exception;
}
