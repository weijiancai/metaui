package com.metaui.eshop.api.taobao;

import com.metaui.core.util.JSoupParser;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0
 */
public class TaoBaoTesterTest {
    @Test
    public void getToken() throws Exception {
        TaoBaoTester tester = new TaoBaoTester();
        String token = tester.getToken();
        System.out.println(token);
    }

    @Test
    public void test() throws Exception {
        Account account = new Account();
        account.setKey("21499544");
        account.setSecret("51fd2ae7d1ecfc8f4775fba746b866b2");
        account.setToken("61021010c1f5c7923e9e6f23cae61503bee5e9caee72738737854810");

        ApiInfo info = new ApiInfo();
        info.setId("taobao.trade.fullinfo.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "tid,type,status,payment,orders");
        params.put("tid", "1526233859431677");

        TaoBaoTester tester = new TaoBaoTester();
        String result = tester.test(account, info, params);
        System.out.println(result);
    }

    @Test
    public void testItemSellerGet() throws Exception {
        Account account = new Account();
        account.setKey("21499544");
        account.setSecret("51fd2ae7d1ecfc8f4775fba746b866b2");
        account.setToken("61021010c1f5c7923e9e6f23cae61503bee5e9caee72738737854810");

        ApiInfo info = new ApiInfo();
        info.setId("taobao.item.seller.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "product_id");
        params.put("num_iid", "527287712924");

        TaoBaoTester tester = new TaoBaoTester();
        String result = tester.test(account, info, params);
        System.out.println(result);
    }

    @Test
    public void testSendBox() throws Exception {
        Account account = new Account();
        account.setKey("1021499544");
        account.setSecret("sandbox7d1ecfc8f4775fba746b866b2");
        account.setToken("6100e03e96411adb578b8a82cb0118e5b270098223eb9c42074082786");
        account.setSandbox(true);

        ApiInfo info = new ApiInfo();
        info.setId("taobao.trade.fullinfo.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "tid,type,status,payment,orders");
        params.put("tid", "262836597896637");

        TaoBaoTester tester = new TaoBaoTester();
        String result = tester.test(account, info, params);
        System.out.println(result);
    }

    @Test
    public void testTaoBaoSendBox() throws Exception {
        Account account = new Account();
        account.setKey("test");
        account.setSecret("test");
        account.setToken("61000069aac7ab1b08339c893846e7575fd340c878b9b932074082786");
        account.setSandbox(true);

        /*ApiInfo info = new ApiInfo();
        info.setId("taobao.trade.fullinfo.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "tid,type,status,payment,orders");
        params.put("tid", "194145140048627");*/

        ApiInfo info = new ApiInfo();
        info.setId("taobao.items.onsale.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "approve_status,num_iid,title,nick,type,cid,product_id,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id,sold_quantity");
//        params.put("cid", "33");
//        params.put("start_modified", "2000-01-01 00:00:00");
//        params.put("is_cspu", "false");
//        params.put("q", "沙箱测试");

        TaoBaoTester tester = new TaoBaoTester();
        String result = tester.test(account, info, params);
        System.out.println(result);
    }

    @Test
    public void testZtTaoBaoSendBox() throws Exception {
        Account account = new Account();
        account.setKey("1021499544");
        account.setSecret("sandbox7d1ecfc8f4775fba746b866b2");
        account.setToken("6100e03e96411adb578b8a82cb0118e5b270098223eb9c42074082786");
        account.setSandbox(true);

        /*ApiInfo info = new ApiInfo();
        info.setId("taobao.trade.fullinfo.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "tid,type,status,payment,orders");
        params.put("tid", "194145140048627");*/

        ApiInfo info = new ApiInfo();
        info.setId("taobao.trades.sold.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "tid,type,status,payment,orders");
//        params.put("cid", "33");
//        params.put("start_modified", "2000-01-01 00:00:00");
//        params.put("is_cspu", "false");
//        params.put("q", "沙箱测试");

        TaoBaoTester tester = new TaoBaoTester();
        String result = tester.test(account, info, params);
        System.out.println(result);
    }

    @Test
    public void testGetTbTestSessionKey() throws IOException {
        String sessionKey = new TaoBaoTester().getTbTestSessionKey();
        System.out.println(sessionKey);
    }

    @Test
    public void testGetToken() throws IOException {
        String url = "https://open.koudaitong.com/oauth/token";
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "c0d689465628e6411a");
        params.put("client_secret", "34553e0c0ffd3887b4a3600832196971");
        params.put("grant_type", "authorization_code");
        params.put("code", "95b487e98f5249689bf5148feeffc1cf73a42f95");
        params.put("redirect_uri", "http://www.abc.com");

        Document doc = new JSoupParser(url).parse(params);
        System.out.println(doc.html());
    }
}