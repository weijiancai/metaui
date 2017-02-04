package ts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.metaui.core.parser.http.JSoupParser;
import com.metaui.core.util.UIO;
import com.metaui.core.util.UString;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.net.ssl.*;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class JSoupParserTest {
    @Test
    public void testParse() throws Exception {
        String url = "http://category.dangdang.com/?ref=www-0-C#ref=www-0-C";
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        Elements elements = doc.select("div#bd div.col_1 div.cfied-list");

//        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<BookCat> catList = new ArrayList<BookCat>();
        int sortNum = 0;
        int num = 0;
        for (Element element : elements) {
            String key = element.select("h4 a").get(0).text();
            BookCat parent = new BookCat(sortNum++ + "", key, "root", num += 10);
            catList.add(parent);
//            List<String> list = new ArrayList<String>();
            int n = 0;
            for(Element e : element.select("div.list a")) {
//                list.add(e.text());
                catList.add(new BookCat(sortNum++ + "", e.text(), parent.id, n += 10));
            }
//            map.put(key, list);
        }
        /*for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("------------------------------------------------");
        }*/
        for (BookCat cat : catList) {
            String sql = String.format("INSERT INTO ss_dz_sale_class ( ss_dz_sale_class.sale_class_id,ss_dz_sale_class.sale_class_name,ss_dz_sale_class.sale_class_super,ss_dz_sale_class.sort_num,ss_dz_sale_class.is_valid,ss_dz_sale_class.input_date,ss_dz_sale_class.o_id_input,ss_dz_sale_class.memo) " +
                    "values ('%s','%s','%s',%d,'T',convert(datetime,'2013-09-11 16:11:17'),'28CAFCFFEEF54F54A4F8B5598571F5F5',null);", cat.id, cat.name, cat.pid, cat.sortNum);
            System.out.println(sql);
        }
    }

    @Test
    public void test() throws IOException {
        String url = "http://www.fentouwang.com/vote/poll.php?action=choose&id=4&choose_value=4057&handlekey=polljs&inajax=1&ajaxtarget=pollmsg_4057";
        url = "http://localhost:8080/tpl/metronic/index.ftl";
        url = "http://192.168.0.103/1.php";
        /*Connection conn = Jsoup.connect(url);
        Document doc = conn.get();
        System.out.println(conn.execute().cookies());
        System.out.println(doc.html());*/
        JSoupParser parser = new JSoupParser(url);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-forwarded-for", "195.253.88.123");
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("5QzN_2132_lastact", "1411653613%09misc.php%09so");
        Document doc = parser.parse(null, headers, cookies);
        System.out.println(doc.html());
    }

    @Test
    public void setRegist() throws IOException {
        /*String registUrl = "http://www.songyuan163.com/Registr.html";
        Connection conn = Jsoup.connect(registUrl);
        conn.get();
        Map<String, String> cookies = conn.response().cookies();
        System.out.println(cookies);
        String pic = "http://www.songyuan163.com/index/verify.html";
        UFile.write(new URL(pic), new File("d:/songyuan"));*/
        String url = "http://www.songyuan163.com/registr/adddata.html";
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("PHPSESSID", "24b4cbe9abd1b0e5d5ee1a51974f6149");
        Map<String, String> data = new HashMap<String, String>();
        data.put("uid", "wei_jc2");
        data.put("pwd", "12345678");
        data.put("tel", "13252515009");
        data.put("email", "weijiancai@126.com");
        data.put("__hash__", "b209f84da4f28bbc679b9a75ff6f78da_9848e55bc847049875624bea11e2a2ef");
        data.put("pwd", "12345678");
        Document doc = Jsoup.connect(url).data(data).cookies(cookies).post();
        System.out.println(doc.html());
//        UFile.write(doc.html(), "D:/songyuan/1.html");
    }

    class BookCat {
        String id;
        String pid;
        String name;
        int sortNum;

        BookCat(String id, String name, String pid, int sortNum) {
            this.id = id;
            this.name = name;
            this.pid = pid;
            this.sortNum = sortNum;
        }

        @Override
        public String toString() {
            return "BookCat{" +
                    "id='" + id + '\'' +
                    ", pid='" + pid + '\'' +
                    ", name='" + name + '\'' +
                    ", sortNum=" + sortNum +
                    '}';
        }
    }

    @Test
    public void testBaobiao() throws IOException {
        PrintWriter pw = new PrintWriter("d:/超级贴身保镖.txt");
//        for (int i = 12668679; i < 12707915; i++) {
        for(String str : getZhang()) {
//            String url = "http://www.23hh.com/book/43/43149/" + i + ".html";
            String url = "http://www.177tvbxs.net/files/article/html/38/38770/" + str;
            JSoupParser parser = new JSoupParser(url);
            Document doc = parser.parse();
            String head = doc.select("div.readerTitle > h1").get(0).text();
            System.out.println(head);
            pw.println(head);
            String text = doc.select("div#content").get(0).html().replace("<br />", "\r\n").replace("&nbsp;", " ");
//            System.out.println(text);
//            System.out.println(doc.html());
            pw.print(text);
            pw.println("\r\n");
            pw.flush();
        }
        pw.close();
    }


    public List<String> getZhang() throws IOException {
        String url = "http://www.177tvbxs.net/files/article/html/38/38770/";
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        Elements elements = doc.select("div.readerListShow > table td a");
        List<String> list = new ArrayList<String>();
        boolean flag = false;
        for (Element element : elements) {
            String head = element.text();
            String urlStr  = element.attr("href");
            System.out.println(head + " --> " + urlStr);
            if (head.contains("蛊族来人")) {
                flag = true;
            }
            if (flag) {
                list.add(urlStr);
            }
        }
        return list;
    }

    @Test
    public void testConnect() throws IOException {
        String url = "http://www.capub.cn/pdm";
        JSoupParser parser = new JSoupParser(url);
        Connection conn = parser.connect().userAgent("Mozilla/5.0 (Windows NT 6.1; rv:23.0) Gecko/20100101 Firefox/23.0");
        Document doc = conn.get();
        System.out.println(conn.response().cookies());
        System.out.println(doc.html());
    }

    @Test
    public void testCargo() throws IOException {
        String url = "http://cargo2.ce-air.com/MU/Service/FlightQuery.aspx?strCul=zh-CN";
        Map<String, String> data = new HashMap<String, String>();
        data.put("cmbSelValue", "MU");
        data.put("fltdate", "1; and (select count(*) from admin) > 0 ");
        data.put("fltmonth", "11");
        data.put("fltyear", "2013");
        data.put("txtCarrier", "MU");
        data.put("txtFltNo", "5112");
        data.put("__VIEWSTATE", "/wEPDwULLTE1MjQzNzA0ODNkZOit4tU1CmAFGuZAgTt1xr9NjgaGSVz0MPj34xi/sXz0");
        data.put("__EVENTVALIDATION", "/wEWAgL7/5rSDQKdqtyUBaDUbNVptoj0/9hv2h7s7q25Y4c8DLtRemL+Y43pb7i0");

        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse(data);
        System.out.println(doc.html());
    }

    @Test
    public void testSongYuanVote() throws IOException {
        String url = "http://www.songyuan163.com/";
        Connection conn = Jsoup.connect(url);
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");
        System.out.println(conn.execute().cookies());

        Map<String, String> cookies = conn.response().cookies();
        System.out.println(cookies);
        url = "http://www.songyuan163.com/liao/PlusVote";
        Map<String, String> data = new HashMap<String, String>();
        data.put("ID", "30");

        /*JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse(data, headers, cookies);
        System.out.println(doc.html());*/
        conn = Jsoup.connect(url);
        conn.header("myreq", "1637f2b4e86554f0ffbc21ea06f8756d");
        conn.header("myver", "3136333766326234653836353534663066666263323165613036663837353664");
        conn.header("x-forwarded-for", "192.168.1.2");
        Document doc = conn.cookies(cookies).data(data).post();
        System.out.println(doc.html());
    }

    @Test
    public void testTaobao() throws Exception {
        String url = "https://login.taobao.com/member/login.jhtml";
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        System.out.println(doc.html());
    }

    @Test
    public void testEcargoWeb() throws Exception {
        String url = "http://localhost:9001/ecargo/policyQuery!clientPolicyQuery.action";

        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("ADMINCONSOLESESSION", "DZyqW20LfxTnk2HpjsCC09jxPn9MQv14FLQc8BTLQkyJrhqp0lGs!1072346402");
        cookies.put("JSESSIONID", "QT1tW22RR6wn4JdyBx0RL9wY0K6FTThSBc53NCkG1RJpQhFGyCyn!1072346402");
        cookies.put("sid", "QT1tW22RR6wn4JdyBx0RL9wY0K6FTThSBc53NCkG1RJpQhFGyCyn%211072346402%211458992817107");

        Map<String, String> data = new HashMap<String, String>();
        data.put("ifflag", "false");
        data.put("beginDate", "2016-03-16");
        data.put("endDate", "2016-03-26");
        data.put("policyStatus", "61");
        data.put("insuranceID", "1");
        data.put("buid", "39262");
        /*data.put("", "");
        data.put("", "");
        data.put("", "");
        data.put("", "");*/

        Connection conn = Jsoup.connect(url);
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");
        System.out.println(conn.execute().cookies());

        conn = Jsoup.connect(url);
        conn.header("myreq", "1637f2b4e86554f0ffbc21ea06f8756d");
        conn.header("myver", "3136333766326234653836353534663066666263323165613036663837353664");
        conn.header("x-forwarded-for", "192.168.1.2");
        Document doc = conn.cookies(cookies).data(data).post();
        System.out.println(doc.html());
    }

    @Test
    public void testWeibo() throws Exception {
        String url = "http://login.weibo.cn/login/";
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
//        System.out.println(doc.html());
        Map<String, String> data = new HashMap<>();
        List<Element> list = doc.body().select("input");
        for (Element element : list) {
            String name = element.attr("name");
            String value = element.attr("value");
            if (name.startsWith("mobile")) {
                data.put(name, "2242860137@qq.com");
            } else if(name.startsWith("password")) {
                data.put(name, "yangjing");
            } else {
                data.put(name, value);
            }
        }
        System.out.println(data);
        String action = doc.select("form").attr("action");
        System.out.println(action);
        url += action;
        parser.setUrl(url);
        doc = parser.parse(data);
        System.out.println(doc.html());
    }

    /**
     * 最新县及县以上行政区划代码（截止2014年10月31日）
     *
     * @throws Exception
     */
    @Test
    public void testXzqhdm() throws Exception {
        String url = "http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201608/t20160809_1386477.html";
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
//        System.out.println(doc.html());
        Map<String, String> data = new HashMap<>();
        List<Element> list = doc.body().select("div.xilan_con > div.TRS_Editor > div.TRS_PreAppend > p > span:eq(1)");
        String province = "";
        String city = "";
        String county;
        for (Element element : list) {
            String name = element.text();
            if (name.substring(0, 3).equals("　　　") || "栾城区".equals(name)) {
                county = UString.trim(name);
//                System.out.println(String.format("insert into dz_temp (province,city,county) values('%s','%s','%s');", province, city, county));
            } else if(name.substring(0, 2).equals("　　")) {
                city = UString.trim(name);
            } else if(name.substring(0, 1).equals("　")) {
                province = UString.trim(name);
                System.out.println(name);
            }
        }
        /*System.out.println(data);
        String action = doc.select("form").attr("action");
        System.out.println(action);
        url += action;
        parser.setUrl(url);
        doc = parser.parse(data);
        System.out.println(doc.html());*/
    }

    /**
     * 解决Https请求,返回404错误
     */
    private static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryTicket() throws Exception {
        trustEveryone();
        boolean isNotFind = true;
        while (isNotFind) {
            for(int j = 15; j < 18; j++) {
                String url = String.format("https://kyfw.12306.cn/otn/leftTicket/queryT?leftTicketDTO.train_date=2016-09-%02d&leftTicketDTO.from_station=JCF&leftTicketDTO.to_station=BJP&purpose_codes=ADULT", j);
                System.out.println(url);
                String result = IOUtils.toString(new URL(url));
                JSONObject obj = JSON.parseObject(result);
                JSONArray data = obj.getJSONArray("data");
                for(int i =0; i < data.size(); i++) {
                    String rw_num = data.getJSONObject(i).getJSONObject("queryLeftNewDTO").getString("rw_num");
                    String yw_num = data.getJSONObject(i).getJSONObject("queryLeftNewDTO").getString("yw_num");
                    String message = "软卧：" + rw_num + "\r\n" + "硬卧：" + yw_num;
                    System.out.println(message);
                    if (!"无".equals(rw_num) || !"无".equals(yw_num)) {
//                        isNotFind = false;
                        JOptionPane.showMessageDialog(null, message, "火车票：2016-08-" + String.format("%02d", j), JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                Thread.sleep(1000 * 10); // 一分钟间隔
            }
        }

    }

    @Test
    public void testCip() throws IOException {
        String url = "http://www.capub.cn:8888/pdm/business/CipInfoAction.do?method=checkApproveNo";
        Map<String, String> data = new HashMap<>();
        data.put("approveNo", "2012280294");
        data.put("captchaNo", "");
        data.put("approveNo", "chk");
        Map<String, String> cookies = new HashMap<>();
        cookies.put("JSESSIONID", "47DF780FC74C15A42B8B21AE0C0B26C2");
        Document document = Jsoup.connect(url).cookies(cookies).data(data).post();
        System.out.println(document.html());
        PrintWriter pw = new PrintWriter("D:/1.html");
        pw.write(document.html());
        pw.close();
    }
}
