package com.metaui.eshop.api.jingdong;

import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0
 */
public class JingDongParserTest {
    @Test
    public void parse() throws Exception {
        JingDongParser parser = new JingDongParser();
        List<Category> list = parser.parse();

        for (Category category : list) {
            System.out.println(category.getName() + "  --> " + category.getDesc());
        }
    }

    @Test
    public void testParseApiInfos() throws Exception {
        String url = "http://jos.jd.com/api/list.htm?id=62";
        JingDongParser parser = new JingDongParser();
        List<ApiInfo> list = parser.parseApiInfos(url);
        for (ApiInfo info : list) {
            System.out.println(info);
        }
    }

    @Test
    public void testParseApiInfo() throws Exception {
        String url = "http://jos.jd.com//api/detail.htm?apiName=jingdong.dsp.kc.usertag.get&id=1283";
        JingDongParser parser = new JingDongParser();
        ApiInfo info = parser.parseApiInfo(Jsoup.connect(url).get(), new ApiInfo());
        System.out.println(info);
    }
}