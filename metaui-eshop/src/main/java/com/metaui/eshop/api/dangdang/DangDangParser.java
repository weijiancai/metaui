package com.metaui.eshop.api.dangdang;

import com.metaui.core.util.JSoupParser;
import com.metaui.core.util.jaxb.JAXBUtil;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.domain.*;
import com.metaui.eshop.api.ApiParser;
import com.metaui.eshop.api.xml.DangDangXml;
import com.metaui.eshop.moudle.EShopModule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.info;

/**
 * 当当Api解析器
 *
 * @author wei_jc
 * @since 1.0
 */
public class DangDangParser implements ApiParser {
    private static Logger log = LoggerFactory.getLogger(DangDangParser.class);
    // 分类url
    private static final String CATEGORY_URL = "http://open.dangdang.com/index.php?c=documentCenterG3&f=show&page_id=9";

    @Override
    public List<Category> parse() throws Exception {
        List<Category> categories = new ArrayList<Category>();

        for(int i = 1; i < 7; i++) {
            categories.add(parseCategory(CATEGORY_URL + i));
            if (i == 1) {
                break;
            }
        }

        // 保存
        DangDangXml xml = new DangDangXml();
        xml.setCategories(categories);
        xml.save();

        return categories;
    }

    private Category parseCategory(String url) throws IOException {
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        String name = doc.select("div.api_list > h1").text();
        String desc = doc.select("ul.detail_list li").get(0).select("div.theme_content > p").text();

        List<Element> elements = doc.select("table.kindTable1 tr ");
        List<ApiInfo> infos = new ArrayList<ApiInfo>();
        for (Element element : elements) {
            String apiUrl = element.select("a").attr("href");
            System.out.println("apiUrl:" + apiUrl);
            infos.add(parseApiInfo(apiUrl));
            break;
        }

        Category category = new Category();
        category.setName(name);
        category.setDesc(desc);
        category.setApiInfos(infos);

        return category;
    }

    private ApiInfo parseApiInfo(String url) throws IOException {
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        String id = doc.select("div.api_list > h1").text();
        String name = doc.select("div.api_list > h3").text();
        String desc = doc.select("ul.detail_list li").get(0).select("theme_content").html();
        String sysParamsUrl = "http://open.dangdang.com/index.php?c=documentCenter&f=show&page_id=89";
        List<ParamInfo> sysParamsList = parseSysParams(sysParamsUrl);
        List<ParamInfo> appParamsList = parseAppParams(doc);
        List<ParamInfo> returnParamsList = parseReturnParams(doc);
        List<CodeExample> resCodesList = parseResCodes(doc);

        ApiInfo info = new ApiInfo();
        info.setId(id);
        info.setName(name);
        info.setDesc(desc);
        info.setSysParams(sysParamsList);
        info.setAppParams(appParamsList);
        info.setReturnParams(returnParamsList);
        info.setResCodes(resCodesList);

        return info;
    }

    private List<CodeExample> parseResCodes(Document doc) throws IOException {
        List<CodeExample> resCodesList = new ArrayList<CodeExample>();
        String codeStr = doc.select("div.right ul.detail_list li").get(3).select("div.theme_content p").text();
        String code = codeStr.substring(codeStr.indexOf("："));

        CodeExample codeExample = new CodeExample();
        codeExample.setLanguage(CodeLanguage.XML);
        codeExample.setCode(code);

        return resCodesList;
    }

    private List<ParamInfo> parseReturnParams(Document doc) throws IOException {
        List<ParamInfo> returnParamsList = new ArrayList<ParamInfo>();

        Elements trElements = doc.select("table.kindTable.kindTable2").get(1).select("tbody tr");
        for (int i = 1; i < trElements.size(); i ++) {
            Element trElement = trElements.get(i);
            String id = trElement.select("td").get(1).text();
            String type = trElement.select("td").get(2).text();
            String name = trElement.select("td").get(0).text();
            String desc = trElement.select("td").get(3).text();

            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setId(id);
            paramInfo.setType(type);
            paramInfo.setName(name);
            paramInfo.setDesc(desc);

            returnParamsList.add(paramInfo);
        }

        return returnParamsList;
    }

    private List<ParamInfo> parseAppParams(Document doc) throws IOException {
        List<ParamInfo> appParamsList = new ArrayList<ParamInfo>();
        Elements trElements = doc.select("table.kindTable.kindTable2").get(0).select("tbody tr");
        for (int i = 1; i < trElements.size(); i ++) {
            Element trElement = trElements.get(i);
            String name = trElement.select("td").get(0).text();
            String id = trElement.select("td").get(1).text();
            String type = trElement.select("td").get(2).text();
            String requireStr = trElement.select("td").get(3).text();
            boolean require = false;
            if(requireStr.equals("Y")) {
                require = true;
            }
            String example = trElement.select("td").get(4).text();
            String desc = trElement.select("td").get(5).text();

            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setId(id);
            paramInfo.setName(name);
            paramInfo.setType(type);
            paramInfo.setRequire(require);
            paramInfo.setExample(example);
            paramInfo.setDesc(desc);

            appParamsList.add(paramInfo);
        }

        return appParamsList;
    }

    private List<ParamInfo> parseSysParams(String url) throws IOException {
        List<ParamInfo> sysParamsList = new ArrayList<ParamInfo>();

        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        Elements trElements = doc.select("div.right ul.detail_list li").get(3).select("div.theme_content div").get(0).select("table.kindTable.kindTable2 > tbody tr");
        for (int i = 1; i < trElements.size(); i ++) {
            Element trElement = trElements.get(i);
            String id = trElement.select("td").get(0).text();
            String type = trElement.select("td").get(1).text();
            String requireStr = trElement.select("td").get(2).text();
            boolean require = false;
            if(requireStr.equals("Y")) {
                require = true;
            }
            String desc = trElement.select("td").get(3).text();

            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setId(id);
            paramInfo.setType(type);
            paramInfo.setRequire(require);
            paramInfo.setDesc(desc);

            sysParamsList.add(paramInfo);
        }

        return sysParamsList;
    }
}
