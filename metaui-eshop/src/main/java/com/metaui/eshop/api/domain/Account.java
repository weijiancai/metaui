package com.metaui.eshop.api.domain;

import com.metaui.eshop.api.ApiSiteName;

import java.io.Serializable;

/**
 * Api接口账号
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/10.
 */
public class Account implements Serializable {
    private ApiSiteName apiSite;
    private String name;
    private String token;
    private String key;
    private String secret;
    private boolean isSandbox;

    public ApiSiteName getApiSite() {
        return apiSite;
    }

    public void setApiSite(ApiSiteName apiSite) {
        this.apiSite = apiSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isSandbox() {
        return isSandbox;
    }

    public void setSandbox(boolean sandbox) {
        isSandbox = sandbox;
    }

    @Override
    public String toString() {
        return name;
    }
}
