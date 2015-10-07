package com.meteorite.core.parser.http;

import com.metaui.core.parser.http.FetchWebSite;
import org.junit.Test;

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
        String baseUrl = "http://xixilu.org/";
        File dir = new File("D:\\fetch\\xixilu");
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
}
