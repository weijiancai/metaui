package com.metaui.eshop.api.jingdong;

import com.metaui.core.util.UDate;
import com.metaui.core.util.format.CodeFormatFactory;
import com.metaui.eshop.api.ApiTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.taobao.TaoBaoTester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0
 */
public class JingDongTester implements ApiTester {
    private static final String URL = "http://gw.api.360buy.com/routerjson";
    private static final int TIMEOUT = 1000 * 2;

    @Override
    public String test(Account account, ApiInfo api, Map<String, String> params) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("method", api.getId());
        data.put("timestamp", UDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        data.put("format", "json");
        data.put("app_key", account.getKey());
        data.put("v", "2.0");
//        data.put("sign_method", "md5");
        data.put("session", account.getToken());

        data.putAll(params);
        data.put("sign", TaoBaoTester.signTopRequest(data, account.getSecret()));

        Document doc = Jsoup.connect(URL).data(data).timeout(TIMEOUT).post();
        String json = doc.body().html();

        return CodeFormatFactory.json(json);
    }
}
