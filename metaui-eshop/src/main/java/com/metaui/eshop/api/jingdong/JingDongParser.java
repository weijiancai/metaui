package com.metaui.eshop.api.jingdong;

import com.metaui.core.util.JSoupParser;
import com.metaui.core.util.UString;
import com.metaui.eshop.api.ApiParser;
import com.metaui.eshop.api.dangdang.DangDangParser;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.api.domain.ParamInfo;
import com.metaui.eshop.api.xml.JingDongXml;
import com.metaui.eshop.api.xml.TaoBaoXml;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 京东Api解析器
 *
 * @author wei_jc
 * @since 1.0
 */
public class JingDongParser implements ApiParser {
    private static Logger log = LoggerFactory.getLogger(JingDongParser.class);
    // 分类url
    private static final String CATEGORY_URL = "http://jos.jd.com/api/index.htm";

    @Override
    public List<Category> parse() throws Exception {
        List<Category> categories = new ArrayList<Category>();

        Document doc = Jsoup.connect(CATEGORY_URL).get();
        Elements elements = doc.select("section.josRight > dl");
        for(Element element : elements) {
            String url = element.select("dt > a").attr("href");
            if (UString.isEmpty(url)) {
                continue;
            }
            url = "http://jos.jd.com/" + url;
            log.debug(url);
            String name = element.select("dt > a").text();
            String desc = element.select("dd").text();

            Category category = new Category();
            category.setName(name);
            category.setDesc(desc);
            category.setApiInfos(parseApiInfos(url));

            categories.add(category);
        }

        // 保存
        JingDongXml xml = new JingDongXml();
        xml.setCategories(categories);
        xml.save();

        return categories;
    }

    public List<ApiInfo> parseApiInfos(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("table.apiListTable > tbody > tr");

        List<ApiInfo> list = new ArrayList<>();
        if (elements.size() == 0) {
            return list;
        }

        for (Element element : elements) {
            String subUrl = element.select("td").get(1).select("a").attr("href");
            if (UString.isEmpty(subUrl)) {
                continue;
            }
            subUrl = "http://jos.jd.com/" + subUrl;
            log.debug("    " + subUrl);

            String id = element.select("td").get(1).select("a").text();
            String name = element.select("td").get(2).text();

            doc = Jsoup.connect(subUrl).timeout(50000).get();
            ApiInfo info = new ApiInfo();
            info.setId(id);
            info.setName(name);
            info.setUrl(subUrl);

            list.add(parseApiInfo(doc, info));
        }


        return list;
    }

    public ApiInfo parseApiInfo(Document doc, ApiInfo info) throws IOException {
        String desc = doc.select("div.articleCon").get(0).text();
        List<ParamInfo> appParamsList = parseAppParams(doc);

        info.setDesc(desc);
        info.setAppParams(appParamsList);

        return info;
    }

    private List<ParamInfo> parseAppParams(Document doc) throws IOException {
        List<ParamInfo> appParamsList = new ArrayList<ParamInfo>();
        Elements tableElements = doc.select("table.josTable");
        if (tableElements.size() < 2) {
            return new ArrayList<>();
        }
        Elements trElements = tableElements.get(1).select("> tbody > tr");

        for (int i = 1; i < trElements.size(); i++) {
            Element trElement = trElements.get(i);
            Elements tds = trElement.select("td");
            if (tds.size() < 5) {
                continue;
            }
            String id = UString.trim(tds.get(0).ownText());
//            String name = tds.get(1).text();
            String type = tds.get(1).text();
            String requireStr = tds.get(2).text();
            boolean require = "是".equals(requireStr);
            String example = tds.get(3).text();
            String desc = tds.get(4).text();

            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setId(id);
//            paramInfo.setName(name);
            paramInfo.setType(type);
            paramInfo.setRequire(require);
            paramInfo.setExample(example);
            paramInfo.setDesc(desc);

            appParamsList.add(paramInfo);
        }

        return appParamsList;
    }
}
