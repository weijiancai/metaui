package com.metaui.eshop.api.dangdang;

import com.metaui.core.util.UDate;
import com.metaui.core.util.format.CodeFormatFactory;
import com.metaui.eshop.api.ApiTester;
import com.metaui.eshop.api.domain.Account;
import com.metaui.eshop.api.domain.ApiInfo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 当当Api接口测试
 *
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/10.
 */
public class DangDangTester implements ApiTester {
    public static final String URL = "http://api.open.dangdang.com/openapi/rest?v=1.0";

    @Override
    public String test(Account account, ApiInfo api, Map<String, String> params) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("method", api.getId());
        data.put("timestamp", UDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        data.put("format", "xml");
        data.put("app_key", account.getKey());
        data.put("v", "1.0");
        data.put("sign_method", "md5");
        data.put("session", account.getToken());

        String str = String.format("%sapp_key%sformatxmlmethod%ssession%ssign_methodmd5timestamp%sv1.0%s", account.getSecret(), account.getKey(), api.getId(),
                account.getToken(), data.get("timestamp"), account.getSecret());
        data.put("sign", md5(str));
        data.putAll(params);

        Document doc = Jsoup.connect(URL).data(data).get();

//        return CodeFormatFactory.xml(doc.html());
        return  doc.html();
    }

    private String md5(String content) throws NoSuchAlgorithmException {
        //根据MD5算法生成MessageDigest对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = content.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();

        return parseByte2HexStr(resultBytes);
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
}
