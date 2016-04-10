package com.metaui.eshop.api.dangdang;

import com.metaui.eshop.api.ApiFactory;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/10.
 */
public class DangDangTesterTest {
    @Test
    public void test1() throws Exception {
        Account account = ApiFactory.getAccount(ApiSiteName.DANG_DANG, "当当-中图城旗舰店");
        ApiInfo api = new ApiInfo();
        api.setId("dangdang.order.details.get");
        DangDangTester tester = new DangDangTester();
        Map<String, String> params = new HashMap<>();
        params.put("o", "12926904002");
        String result = tester.test(account, api, params);
        System.out.println(result);
    }

}