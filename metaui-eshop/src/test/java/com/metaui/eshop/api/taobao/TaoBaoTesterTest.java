package com.metaui.eshop.api.taobao;

import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
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
    public void testSendBox() throws Exception {
        Account account = new Account();
        account.setKey("1021499544");
        account.setSecret("sandbox7d1ecfc8f4775fba746b866b2");
        account.setToken("610122955a1dcaafc7b4050785012c164de761e665168242074082786");
        account.setSandbox(true);

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
    public void testTaoBaoSendBox() throws Exception {
        Account account = new Account();
        account.setKey("test");
        account.setSecret("test");
        account.setToken("61022110c46ff0ec3e611aab62c7e25ecbc045bc2841f9f2074082786");
        account.setSandbox(true);

        ApiInfo info = new ApiInfo();
        info.setId("taobao.trade.fullinfo.get");

        Map<String, String> params = new HashMap<>();
        params.put("fields", "tid,type,status,payment,orders");
        params.put("tid", "194145140048627");

        TaoBaoTester tester = new TaoBaoTester();
        String result = tester.test(account, info, params);
        System.out.println(result);
    }

    @Test
    public void testGetTbTestSessionKey() throws IOException {
        String sessionKey = new TaoBaoTester().getTbTestSessionKey();
        System.out.println(sessionKey);
    }
}