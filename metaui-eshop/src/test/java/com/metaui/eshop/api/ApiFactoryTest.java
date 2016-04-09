package com.metaui.eshop.api;

import com.metaui.eshop.api.domain.Category;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/9.
 */
public class ApiFactoryTest {
    @Test
    public void getApi() throws Exception {
        List<Category> list = ApiFactory.getApi(ApiSiteName.DANG_DANG);
        for (Category category : list) {
            System.out.println(category.getName() + "  --> " + category.getDesc());
        }
    }

}