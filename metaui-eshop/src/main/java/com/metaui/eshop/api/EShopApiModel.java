package com.metaui.eshop.api;

import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.Category;

import java.util.List;

/**
 * Api接口模型
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/17.
 */
public class EShopApiModel {
    public List<ApiSiteName> getApiSites() {
        return ApiSiteName.getAll();
    }

    public List<Account> getAccount(ApiSiteName siteName) {
        return ApiFactory.getAccounts(siteName);
    }

    public void saveAccount(Account account) throws Exception {
        ApiFactory.addAccount(account);
    }

    public List<Category> getCategory(ApiSiteName siteName) throws Exception {
        return ApiFactory.getApi(siteName);
    }
}
