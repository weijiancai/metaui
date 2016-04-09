package com.metaui.eshop.api.dangdang;

import com.metaui.core.util.JSoupParser;
import com.metaui.core.util.jaxb.JAXBUtil;
import com.metaui.eshop.api.ApiSiteName;
import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import com.metaui.eshop.api.ApiParser;
import com.metaui.eshop.api.xml.DangDangXml;
import com.metaui.eshop.moudle.EShopModule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        ApiInfo info = new ApiInfo();
        info.setId(id);
        info.setName(name);
        info.setDesc(desc);

        return info;
    }
}
