package com.meteorite.core.parser.http;

import com.alibaba.fastjson.JSON;
import com.metaui.core.parser.http.FetchWebSite;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FetchWebSiteTest {
    @Test
    public void testFetchWebSite() throws IOException {
        String baseUrl = "http://www.0731hunjia.com/";
        File dir = new File("D:\\fetch\\0731hunjia");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.addExcludeUrl("http://www.0731hunjia.com/bbs");
        fetchWebSite.fetch(baseUrl, 0);
    }

    @Test
    public void testFetchNCargo() throws IOException {
        String baseUrl = "http://cargo2.ce-air.com/MU/";
        File dir = new File("D:\\fetch\\ncargo");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetch(baseUrl, 0);
    }

    @Test
    public void testFetchPage() throws IOException {
        String baseUrl = "http://passport.jd.com/uc/login?ltype=logout";
        File dir = new File("D:\\fetch\\jd");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        /*fetchWebSite.addExcludeUrl("http://www.0731hunjia.com/bbs");*/
        fetchWebSite.fetch(baseUrl, 0);
    }

    @Test
    public void testFetchBookPage() throws IOException {
        String baseUrl = "http://item.jd.com/16001491.html";
        /*File dir = new File("D:\\fetch\\jd");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetch(baseUrl, 0);*/

        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gb2312"));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    @Test
    public void testFetchQstbook() throws IOException {
        String baseUrl = "http://www.qstbook.com/member/order/confirmOrder";
        File dir = new File("D:\\fetch\\qstbook");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetch(baseUrl, 1);
    }

    @Test
    public void testFetchImages() throws IOException {
//        String baseUrl = "http://www.7-zhou.com/qizhoujkys/yangsheng_1.html?ext=MjQz3NCwzMjIzLHNpbmthLDI4ODAsMzg0NiwzODQ2LA==";
//        String baseUrl = "http://xixilu.org/";
//        String baseUrl = "http://2222AV.CO";
        String baseUrl = "http://www.xixirenti.net/";
        File dir = new File("D:\\fetch\\xixirenti");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetchImages(baseUrl);
    }

    @Test
    public void testMetronic() throws IOException {
        String baseUrl = "http://www.keenthemes.com/preview/metronic/theme/templates/admin/";
        File dir = new File("D:\\fetch\\metronic");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetch(baseUrl, 20);
    }

    @Test
    public void testIYueSai() throws IOException { 
        String baseUrl = "http://www.iyuesai.com/game/416";
        File dir = new File("D:\\fetch\\iyuesai");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetch(baseUrl, 1);
    }

    @Test
    public void testWeiXin() throws IOException {
        String baseUrl = "http://weixin.sogou.com/websearch/art.jsp?sg=CBf80b2xkgYOkNYQSwkwTeOfXrNIkcER7NiOstNMQ0da3TIJh2dES5p1ZP1vJcoCAJTypjE1HX8Ox4ChZ0cnGOK1aUawqwGgU-aATq-GBOz0ycy5BK9qHUqtmU7-GUYzbPevHhqM9RMhnCWSpbO3WQ..&url=p0OVDH8R4SHyUySb8E88hkJm8GF_McJfBfynRTbN8wi8A0Xyo7k9AMGh94WqM6eaFJmAPOq06MxIF9gV-jJIUVMj362uDS1pzRj-eLVYcd34oUyP5UWFMs_M7Och-Fqw1kV8q2biIAZYy-5x5In7jJFmExjqCxhpkyjFvwP6PuGcQ64lGQ2ZDMuqxplQrsbk";
        File dir = new File("D:\\fetch\\weixin");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.fetch(baseUrl, 1);
    }

    @Test
    public void testImgWidthHeight() throws Exception {
        /*String url = "http://p.xixirenti.net/uploadfile/2016/1231/46/01.jpg";
        BufferedImage image = ImageIO.read(new URL(url));
        System.out.println("width = " + image.getWidth() + ",height = " + image.getHeight());*/

//        String url = "http://www.xixirenti.org/yazhourenti/2016/0824/13_22.html";
        String url = "http://www.xixirenti.org/yazhourenti/2016/";
        /*for(int i = 1; i <= 12; i++) { // 月
            for(int j = 1; j <= 31; j++) { // 日
                for(int k = 1; k <= 1000; k++) { // 页面
                    String href = url + String.format("%02d%02d/%d.html", i, j, k);
                    try {
                        System.out.println(href + "  " + Jsoup.connect(href).execute().statusCode());
                    } catch (Exception e) {
                        break;
                    }


                    for(int m = 1; m <= 100; m++) { // 分页
                        href = url +  String.format("%02d%02d/%d_%d.html", i, j, k, m);
                        System.out.println(href);
                    }
                }
            }
        }*/
        File dir = new File("D:\\fetch\\xixirenti_1");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);

        url = "http://www.xixirenti.org/yazhourenti/2016/0825/";
        for(int k = 1; k <= 1000; k++) { // 页面
            String href = url + String.format("%d.html", k);
            try {
                System.out.println(href);
                System.out.println(href + "  " + Jsoup.connect(href).execute().statusCode());
            } catch (Exception e) {
                continue;
            }


            for(int m = 2; m <= 100; m++) { // 分页
                href = url +  String.format("%d_%d.html", k, m);
                System.out.println(href);
                try {
//                    System.out.println(href + "  " + Jsoup.connect(href).execute().statusCode());
                    fetchWebSite.fetchImages(href);
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    @Test
    public void testGetBook() throws IOException {
        String url = "http://192.168.31.109:8000/share/books/";
        String body = Jsoup.connect(url).ignoreContentType(true).execute().body();
        System.out.println(body);
    }
}
