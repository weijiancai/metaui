package com.metaui.eshop.api.amazon;

import com.metaui.eshop.api.ApiTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;

import java.util.Map;

/**
 * 亚马逊Api接口测试
 *
 * @author wei_jc
 * @since 1.0
 */
public class AmazonTester implements ApiTester {
    public static final String URL = "https://mws.amazonservices.com/";

    @Override
    public String test(Account account, ApiInfo api, Map<String, String> params) throws Exception {

        return null;
    }
}
