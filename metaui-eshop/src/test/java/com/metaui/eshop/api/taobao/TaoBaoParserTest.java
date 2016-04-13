package com.metaui.eshop.api.taobao;

import com.metaui.eshop.api.domain.ApiInfo;
import com.metaui.eshop.api.domain.Category;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0
 */
public class TaoBaoParserTest {
    @Test
    public void parse() throws Exception {
        TaoBaoParser parser = new TaoBaoParser();
        List<Category> list = parser.parse();
        for (Category category : list) {
            System.out.println(category.getName() + "  --> " + category.getDesc());
        }
    }

    @Test
    public void testParseApiInfo() throws IOException {
//        String url = "http://open.taobao.com//doc2/apiDetail.htm?apiId=24820";
        String url = "http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.dMDLYS&apiId=10632";
        TaoBaoParser parser = new TaoBaoParser();
        ApiInfo info = parser.parseApiInfo(url);
        System.out.println(info);
    }
}