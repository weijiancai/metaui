package com.metaui.eshop.api.taobao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.metaui.core.util.UDate;
import com.metaui.core.util.UString;
import com.metaui.eshop.api.ApiTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 淘宝Api接口测试
 * <p>
 * 访问  https://oauth.tbsandbox.com/authorize?response_type=code&client_id=1021499544&redirect_uri=http://mini.tbsandbox.com&state=1212&scope=item&view=web
 * 取到code ，
 * <p>
 * http://open.taobao.com/doc2/detail?spm=a219a.7629140.0.0.XaOfuU&articleId=101617&docType=1
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/10.
 */
public class TaoBaoTester implements ApiTester {
    public static final String URL = "http://gw.api.taobao.com/router/rest";
    public static final String SANDBOX_URL = "http://gw.api.tbsandbox.com/router/rest";
    private static final int TIMEOUT = 1000 * 2;

    @Override
    public String test(Account account, ApiInfo api, Map<String, String> params) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("method", api.getId());
        data.put("timestamp", UDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        data.put("format", "json");
        data.put("app_key", account.getKey());
        data.put("v", "2.0");
        data.put("sign_method", "md5");
        data.put("session", account.getToken());

        data.putAll(params);
        data.put("sign", signTopRequest(data, account.getSecret()));

        String url = account.isSandbox() ? SANDBOX_URL : URL;
        Document doc = Jsoup.connect(url).data(data).timeout(TIMEOUT).post();
        String json = doc.body().html();
        JSONObject obj = JSON.parseObject(json);

        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }

    /**
     * 获得淘宝沙箱test Appkey的SessionKey
     * @return
     */
    public String getTbTestSessionKey() throws IOException {
        String url = "http://mini.tbsandbox.com/tools/getSessionKey.htm?appkey=test&submit=%E6%90%9C+%E7%B4%A2";
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc.html());
        return doc.body().select("table.content-table tr").get(2).select("td:eq(2)").text();
    }

    public String getToken() throws IOException {
        Map<String, String> params = new HashMap<>();

        params.put("response_type", "code");
        params.put("client_id", "1021499544");
        params.put("redirect_uri", "http://mini.tbsandbox.com");
        params.put("state", "1212");
        params.put("scope", "item");
        params.put("view", "web");
        params.put("code", "v4OJpGaL6sZJtPAokVDanR0v162");
        params.put("client_secret", "sandbox7d1ecfc8f4775fba746b866b2");
        params.put("grant_type", "authorization_code");

//        Document doc = Jsoup.connect("https://oauth.tbsandbox.com/token").data(params).post();
//        return doc.html();
        WebUtils.setIgnoreSSLCheck(true);
        return WebUtils.doPost("https://oauth.tbsandbox.com/token", params, 30000, 30000);
    }


    public static String signTopRequest(Map<String, String> params, String secret) throws Exception {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);

        for (String key : keys) {
            String value = params.get(key);
            if (UString.isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secret);
        System.out.println(query.toString());

        // 第三步：HMAC加密
        byte[] bytes = md5(query.toString());

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    private static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    private static byte[] md5(String content) throws NoSuchAlgorithmException {
        //根据MD5算法生成MessageDigest对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = content.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result

        return md5.digest();
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
