package com.metaui.core.parser.mobile;

import com.metaui.core.util.Callback;
import com.metaui.core.util.UString;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 从吉号吧网站（http://www.jihaoba.com/tools/?com=haoduan）抓取手机号码
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FetchMobileNumber {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.102 Safari/537.36"; // 用户浏览器信息
    private static final int TIME_OUT = 2 * 60 * 1000; // 2分钟超时
    public static final String BASE_URL = "http://www.jihaoba.com/tools/?com=haoduan";
    private static List<String> codeSegmentList = new ArrayList<String>();

    private Map<String, String> cookies;
    private Callback<List<MobileNumber>> callback;
    private File mobileFile = new File("D:\\workspace\\metaui\\core\\src\\main\\java\\com\\metaui\\core\\parser\\mobile\\mobile.txt");
    private PrintWriter pw;

    public FetchMobileNumber() {
    }

    public FetchMobileNumber(Callback<List<MobileNumber>> callback) {
        this.callback = callback;
    }

    public void fetch() throws Exception {
        pw = new PrintWriter(mobileFile);
        getProvinceUrl();
        pw.close();
    }

    private void getProvinceUrl() throws Exception {
        Connection conn = Jsoup.connect("http://www.jihaoba.com/").userAgent(USER_AGENT);
        Connection.Response response = conn.execute();
        cookies = response.cookies();
        System.out.println(response.cookies());
        Document doc = Jsoup.connect(BASE_URL).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right1 div.nr div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String province = element.text();
//            System.out.println(province + " > " + href);
            if ("北京".equals(province) || "上海".equals(province) || "天津".equals(province) || "重庆".equals(province)) {
                getCardTypeUrl(href, province, province);
            } else {
                getCityUrl(href, province);
            }
        }
    }

    private void getCityUrl(String url, String province) throws Exception {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right1 div.nr div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String city = element.text();
            if (UString.isNotEmpty(city)) {
//                System.out.println("    " + city + " > " + href);
                getCardTypeUrl(href, province, city);
            }
        }
    }

    private void getCardTypeUrl(String url, String province, String city) throws Exception {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right1 div.nr div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String cardType = element.text();
            if (UString.isNotEmpty(cardType)) {
//                System.out.println("    " + cardType + " > " + href);
                getCodeSegmentUrl(href, province, city);
            }
        }
    }

    private void getCodeSegmentUrl(String url, String province, String city) throws Exception {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right3 div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String codeSegment = element.text().replace("*", "");
//            System.out.println("        " + codeSegment + " > " + href);
//            getCodeUrl(href, province, city, codeSegment);
            codeSegmentList.add(codeSegment);
//            System.out.println(String.format("codeSegmentList.add(\"%s\");", codeSegment));
            pw.write(String.format("codeSegmentList.add(\"%s\");\r\n", codeSegment));
        }
        pw.flush();
    }

    private void getCodeUrl(String url, String province, String city, String codeSegment) throws Exception {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements liElement = doc.select("div#right div.right22 div.nr3 div.left li");
        String cardType = liElement.get(2).select("span").get(0).text();
        String operator = liElement.get(3).select("span").get(0).text();

        String codes = doc.select("div#right div.right3 div.nr1 textarea").get(0).text();
        /*System.out.println(cardType + " --> " + operator);
        System.out.println(codes);*/
        List<MobileNumber> list = new ArrayList<MobileNumber>();
        for (String code : codes.split("\u3000")) {
            if(code.length() != 11) continue;

            MobileNumber mobileNumber = new MobileNumber(code, province, city, cardType, operator, codeSegment);
            System.out.println(mobileNumber);
            list.add(mobileNumber);
            pw.write(code + "\r\n");
            pw.flush();
        }

        if (callback != null) {
            callback.call(list);
        }
    }

    public static String random() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 号段 7位
        String codeSegment = codeSegmentList.get(random.nextInt(codeSegmentList.size()));
        sb.append(codeSegment);
        // 流水号 4位
        sb.append(String.format("%04d", random.nextInt(10000)));

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        FetchMobileNumber fetchMobileNumber = new FetchMobileNumber();
        fetchMobileNumber.fetch();
    }
    
    static {
        codeSegmentList.add("1861262");
        codeSegmentList.add("1888888");
        codeSegmentList.add("1801021");
        codeSegmentList.add("1801023");
        codeSegmentList.add("1801027");
        codeSegmentList.add("1801028");
        codeSegmentList.add("1801030");
        codeSegmentList.add("1801037");
        codeSegmentList.add("1801038");
        codeSegmentList.add("1801040");
        codeSegmentList.add("1801041");
        codeSegmentList.add("1801042");
        codeSegmentList.add("1801043");
        codeSegmentList.add("1801044");
        codeSegmentList.add("1801045");
        codeSegmentList.add("1801046");
        codeSegmentList.add("1810000");
        codeSegmentList.add("1810001");
        codeSegmentList.add("1810002");
        codeSegmentList.add("1810003");
        codeSegmentList.add("1810004");
        codeSegmentList.add("1810101");
        codeSegmentList.add("1579108");
        codeSegmentList.add("1579109");
        codeSegmentList.add("1579440");
        codeSegmentList.add("1579441");
        codeSegmentList.add("1579442");
        codeSegmentList.add("1579443");
        codeSegmentList.add("1579444");
        codeSegmentList.add("1579445");
        codeSegmentList.add("1579446");
        codeSegmentList.add("1579447");
        codeSegmentList.add("1579448");
        codeSegmentList.add("1579449");
        codeSegmentList.add("1840100");
        codeSegmentList.add("1840101");
        codeSegmentList.add("1840102");
        codeSegmentList.add("1840104");
        codeSegmentList.add("1840105");
        codeSegmentList.add("1840106");
        codeSegmentList.add("1840107");
        codeSegmentList.add("1840108");
        codeSegmentList.add("1840109");
        codeSegmentList.add("1349036");
        codeSegmentList.add("1349037");
        codeSegmentList.add("1349038");
        codeSegmentList.add("1349039");
        codeSegmentList.add("1349040");
        codeSegmentList.add("1349041");
        codeSegmentList.add("1349042");
        codeSegmentList.add("1349043");
        codeSegmentList.add("1349044");
        codeSegmentList.add("1349046");
        codeSegmentList.add("1349047");
        codeSegmentList.add("1349048");
        codeSegmentList.add("1349049");
        codeSegmentList.add("1349050");
        codeSegmentList.add("1349051");
        codeSegmentList.add("1349052");
        codeSegmentList.add("1349053");
        codeSegmentList.add("1349054");
        codeSegmentList.add("1349055");
        codeSegmentList.add("1349056");
        codeSegmentList.add("1349057");
        codeSegmentList.add("1571106");
        codeSegmentList.add("1571107");
        codeSegmentList.add("1571108");
        codeSegmentList.add("1571109");
        codeSegmentList.add("1571110");
        codeSegmentList.add("1571111");
        codeSegmentList.add("1571112");
        codeSegmentList.add("1571113");
        codeSegmentList.add("1571114");
        codeSegmentList.add("1571115");
        codeSegmentList.add("1571116");
        codeSegmentList.add("1571117");
        codeSegmentList.add("1571118");
        codeSegmentList.add("1571119");
        codeSegmentList.add("1312671");
        codeSegmentList.add("1312672");
        codeSegmentList.add("1312673");
        codeSegmentList.add("1312676");
        codeSegmentList.add("1312677");
        codeSegmentList.add("1312678");
        codeSegmentList.add("1312679");
        codeSegmentList.add("1312680");
        codeSegmentList.add("1312681");
        codeSegmentList.add("1312682");
        codeSegmentList.add("1312683");
        codeSegmentList.add("1312685");
        codeSegmentList.add("1312686");
        codeSegmentList.add("1312687");
        codeSegmentList.add("1312688");
        codeSegmentList.add("1312689");
        codeSegmentList.add("1312690");
        codeSegmentList.add("1312691");
        codeSegmentList.add("1312692");
        codeSegmentList.add("1312693");
        codeSegmentList.add("1312695");
        codeSegmentList.add("1312696");
        codeSegmentList.add("1312697");
        codeSegmentList.add("1312698");
        codeSegmentList.add("1312699");
        codeSegmentList.add("1314100");
        codeSegmentList.add("1314101");
        codeSegmentList.add("1314102");
        codeSegmentList.add("1314103");
        codeSegmentList.add("1314104");
        codeSegmentList.add("1314105");
        codeSegmentList.add("1314106");
        codeSegmentList.add("1326348");
        codeSegmentList.add("1326349");
        codeSegmentList.add("1326400");
        codeSegmentList.add("1326401");
        codeSegmentList.add("1451399");
        codeSegmentList.add("1452000");
        codeSegmentList.add("1452001");
        codeSegmentList.add("1452002");
        codeSegmentList.add("1452003");
        codeSegmentList.add("1452004");
        codeSegmentList.add("1452005");
        codeSegmentList.add("1452006");
        codeSegmentList.add("1452007");
        codeSegmentList.add("1452008");
        codeSegmentList.add("1452009");
        codeSegmentList.add("1452010");
        codeSegmentList.add("1452011");
        codeSegmentList.add("1452012");
        codeSegmentList.add("1452013");
        codeSegmentList.add("1452014");
        codeSegmentList.add("1452015");
        codeSegmentList.add("1452016");
        codeSegmentList.add("1452017");
        codeSegmentList.add("1369302");
    }
}
