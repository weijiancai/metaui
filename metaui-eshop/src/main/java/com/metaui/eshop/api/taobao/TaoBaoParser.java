package com.metaui.eshop.api.taobao;

import com.metaui.core.util.JSoupParser;
import com.metaui.core.util.UString;
import com.metaui.eshop.api.ApiParser;
import com.metaui.eshop.api.dangdang.DangDangParser;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.api.domain.ParamInfo;
import com.metaui.eshop.api.xml.DangDangXml;
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
 * 淘宝Api解析器
 *
 * @author wei_jc
 * @since 1.0
 */
public class TaoBaoParser implements ApiParser {
    private static Logger log = LoggerFactory.getLogger(DangDangParser.class);
    // 分类url
    private static final String CATEGORY_URL = "http://open.taobao.com/doc2/apiList.htm?spm=a219a.7629065.0.0.uCLTJq&docType=";

    @Override
    public List<Category> parse() throws Exception {
        List<Category> categories = new ArrayList<Category>();

        Document doc = Jsoup.connect(CATEGORY_URL).get();
        Elements elements = doc.select("ul.menu-1 > li > a");
        for(Element element : elements) {
            categories.add(parseCategory("http://open.taobao.com/" + element.attr("href")));
        }

        // 保存
        TaoBaoXml xml = new TaoBaoXml();
        xml.setCategories(categories);
        xml.save();

        return categories;
    }

    private Category parseCategory(String url) throws IOException {
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        String name = doc.select("div.docs-right > div.mtl > h2").text();
        String desc = doc.select("div.docs-right > div.mtl > p").text();

        List<Element> elements = doc.select("div#bd > table tr");
        List<ApiInfo> infos = new ArrayList<ApiInfo>();
        for (Element element : elements) {
            Elements tds = element.select("td");
            if (tds.size() > 0) {
                String apiUrl = "http://open.taobao.com/" + tds.get(0).select("a").attr("href");
                System.out.println("apiUrl:" + apiUrl);
                ApiInfo info = parseApiInfo(apiUrl);
                info.setId(tds.get(0).text());
                info.setName(tds.get(2).text());
                infos.add(info);
            }
        }

        Category category = new Category();
        category.setName(name);
        category.setDesc(desc);
        category.setApiInfos(infos);

        return category;
    }

    public ApiInfo parseApiInfo(String url) throws IOException {
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        String id = doc.select("h2.mtl-main").text();
        String name = doc.select("h2.mtl-main > span").text();
        String desc = doc.select("p.mtl-desc").text();
//        List<ParamInfo> sysParamsList = parseSysParams(sysParamsUrl);
        List<ParamInfo> appParamsList = parseAppParams(doc);
//        List<ParamInfo> returnParamsList = parseReturnParams(doc);
//        List<CodeExample> reqCodesList = parseReqCodes(doc);
//        List<CodeExample> resCodesList = parseResCodes(doc);

        ApiInfo info = new ApiInfo();
        info.setId(id);
        info.setName(name);
        info.setDesc(desc);
        info.setUrl(url);
//        info.setSysParams(sysParamsList);
        info.setAppParams(appParamsList);
//        info.setReturnParams(returnParamsList);
//        info.setReqCodes(reqCodesList);
//        info.setResCodes(resCodesList);

        return info;
    }

    private List<ParamInfo> parseAppParams(Document doc) throws IOException {
        List<ParamInfo> appParamsList = new ArrayList<ParamInfo>();
        if (doc.select("div.J_sCon").size() == 0) {
            return new ArrayList<>();
        }
        Elements tableElements = doc.select("div.J_sCon").get(1).select("table.table");
        if (tableElements.size() == 0) {
            return new ArrayList<>();
        }
        Elements trElements = tableElements.get(0).select("> tbody > tr");

        for (Element trElement : trElements) {
            Elements tds = trElement.select("td");
            if (tds.size() == 0 || trElement.hasClass("open-wrap")) {
                continue;
            }
            String id = UString.trim(tds.get(0).ownText());
//            String name = tds.get(1).text();
            String type = tds.get(1).text();
            String requireStr = tds.get(2).text();
            boolean require = "必须".equals(requireStr);
            String example = tds.get(3).text();
            String desc = tds.get(5).text();

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
