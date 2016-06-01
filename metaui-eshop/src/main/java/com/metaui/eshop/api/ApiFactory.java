package com.metaui.eshop.api;

import com.metaui.core.util.UString;
import com.metaui.eshop.api.dangdang.DangDangParser;
import com.metaui.eshop.api.dangdang.DangDangTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.api.jingdong.JingDongParser;
import com.metaui.eshop.api.jingdong.JingDongTester;
import com.metaui.eshop.api.taobao.TaoBaoParser;
import com.metaui.eshop.api.taobao.TaoBaoTester;
import com.metaui.eshop.api.xml.DangDangXml;
import com.metaui.eshop.api.xml.JingDongXml;
import com.metaui.eshop.api.xml.TaoBaoXml;
import com.metaui.eshop.moudle.EShopModule;

import java.io.*;
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
    private static List<Account> accounts = new ArrayList<>();

    static {
        try {
            if (accounts.size() == 0) {
                // 读取
                File file = new File(EShopModule.getPath(), "account");
                if (file.exists()) {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    accounts = (List<Account>) ois.readObject();
                    ois.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            case TAO_BAO: {
                TaoBaoXml xml = new TaoBaoXml();
                xml.load();
                list = xml.getCategories();
                if (list == null) {
                    list = new TaoBaoParser().parse();
                }
                break;
            }
            case JING_DONG: {
                JingDongXml xml = new JingDongXml();
                xml.load();
                list = xml.getCategories();
                if (list == null) {
                    list = new JingDongParser().parse();
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("未知Api站点名称：" + name);
            }
        }

        return list;
    }

    public static ApiTester getTester(ApiSiteName name) throws Exception {
        switch (name) {
            case DANG_DANG: {
                return new DangDangTester();
            }
            case TAO_BAO: {
                return new TaoBaoTester();
            }
            case JING_DONG: {
                return new JingDongTester();
            }
            default: {
                throw new IllegalArgumentException("未知Api站点名称：" + name);
            }
        }
    }

    public static Account getAccount(ApiSiteName site, String name) throws Exception {
        for (Account account : accounts) {
            if (account.getApiSite() == site && account.getName().equals(name)) {
                return account;
            }
        }

        return null;
    }

    public static List<Account> getAccounts(ApiSiteName siteName) {
        List<Account> list = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getApiSite() == siteName) {
                list.add(account);
            }
        }
        return list;
    }

    public static void addAccount(Account account) throws Exception {
        Account ac = getAccount(account.getApiSite(), account.getName());
        if (ac == null) {
            accounts.add(account);
        } else {
            ac.setToken(UString.trim(account.getToken()));
            ac.setKey(UString.trim(account.getKey()));
            ac.setSecret(UString.trim(account.getSecret()));
        }

        // 保存
        File file = new File(EShopModule.getPath(), "account");
        file.delete();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(accounts);
        oos.close();
    }
}
