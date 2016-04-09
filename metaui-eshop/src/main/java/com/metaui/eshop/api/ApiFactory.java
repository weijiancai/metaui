package com.metaui.eshop.api;

import com.metaui.core.util.jaxb.JAXBUtil;
import com.metaui.eshop.api.dangdang.DangDangParser;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.api.xml.DangDangXml;
import com.metaui.eshop.moudle.EShopModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Api接口工厂
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class ApiFactory {
    /**
     * 获得Api接口分类
     *
     * @param name api站点名称
     * @return 返回接口分类列表
     */
    public static List<Category> getApi(ApiSiteName name) throws Exception {
        List<Category> list;

        switch (name) {
            case DANG_DANG: {
                DangDangXml xml = new DangDangXml();
                xml.load();
                list = xml.getCategories();
                if (list == null) {
                    list = new DangDangParser().parse();
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("未知Api站点名称：" + name);
            }
        }

        return list;
    }
}
