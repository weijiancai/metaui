package com.metaui.eshop.api;

import java.util.Arrays;
import java.util.List;

/**
 * 站点名
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum ApiSiteName {
    /**
     * 京东
     */
    JING_DONG("京东"),
    /**
     * 当当
     */
    DANG_DANG("当当"),
    /**
     * 淘宝
     */
    TAO_BAO("淘宝"),
    /**
     * 亚马逊
     */
    AMAZON("亚马逊");

    private String displayName;

    ApiSiteName(String displayName) {
        this.displayName = displayName;
    }


    @Override
    public String toString() {
        return displayName;
    }

    public static List<ApiSiteName> getAll() {
        return Arrays.asList(ApiSiteName.values());
    }
}
