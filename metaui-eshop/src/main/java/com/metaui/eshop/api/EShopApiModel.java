package com.metaui.eshop.api;

import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;

import java.util.Collections;
import java.util.Comparator;
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
        List<Category> list = ApiFactory.getApi(siteName);
        // 排序
        for (Category category : list) {
            if (category.getApiInfos() != null) {
                Collections.sort(category.getApiInfos(), new Comparator<ApiInfo>() {
                    @Override
                    public int compare(ApiInfo o1, ApiInfo o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });
            }
        }
        return list;
    }
}
