package com.metaui.eshop.api.dangdang;

import com.metaui.core.util.JSoupParser;
import com.metaui.eshop.api.ApiCategory;
import com.metaui.eshop.api.ApiParser;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";

    @Override
    public List<ApiCategory> parse() throws Exception {
        List<ApiCategory> categories = new ArrayList<ApiCategory>();

        for(int i = 1; i < 7; i++) {
            categories.add(parseCategory(CATEGORY_URL + i));
        }

        return categories;
    }

    private ApiCategory parseCategory(String url) throws IOException {
//        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        String name = doc.select("div.api_list > h1").text();
        String desc = doc.select("ul.detail_list li").get(0).select("div.theme_content > p").text();

        ApiCategory category = new ApiCategory();
        category.setName(name);
        category.setDesc(desc);

        return category;
    }
}
