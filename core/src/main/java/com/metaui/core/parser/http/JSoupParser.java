package com.metaui.core.parser.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class JSoupParser {
    private String url;
    private Map<String, String> cookies = new HashMap<String, String>();

    public JSoupParser(String url) {
        this.url = url;
    }

    public Document parse() throws IOException {
        return parse(null);
    }

    public Document parse(Map<String, String> data) throws IOException {
        return parse(data, null, null);
    }

    public Document parse(Map<String, String> data, Map<String, String> headers, Map<String, String> cookieMap) throws IOException {
        Connection conn = Jsoup.connect(url).timeout(50000);
//        conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");
        conn.userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4");

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.header(entry.getKey(), entry.getValue());
            }
        }
        Map<String, String> cm = conn.execute().cookies();
        cookies.putAll(cm);

        if (cookieMap != null && cookieMap.size() > 0) {
            cookies.putAll(cookieMap);
        }
        conn.execute().cookies().putAll(cookies);

        System.out.println(cookies);
        if (data != null) {
            conn.data(data);
        }

        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        conn.followRedirects(false);//此行必须添加

        return conn.post();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Connection connect() {
        return Jsoup.connect(url);
    }

    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 2) {
            System.out.println("参数不正确：应为 url ip");
            System.exit(0);
        }
        String url = args[0];
        String ip = args[1];

        JSoupParser parser = new JSoupParser(url);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-forwarded-for", ip);
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("5QzN_2132_lastact", "1411653613%09misc.php%09so");
        Document doc = parser.parse(null, headers, null);
        System.out.println(doc.html());
    }
}
