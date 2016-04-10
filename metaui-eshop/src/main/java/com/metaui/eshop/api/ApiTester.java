package com.metaui.eshop.api;

import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;

import java.io.IOException;
import java.util.Map;

/**
 * Api接口测试
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/10.
 */
public interface ApiTester {
    /**
     * 测试
     *
     * @param account 账号
     * @param api Api接口
     * @param params 请求参数
     * @return 返回结果字符串
     */
    String test(Account account, ApiInfo api, Map<String, String> params) throws Exception;
}
