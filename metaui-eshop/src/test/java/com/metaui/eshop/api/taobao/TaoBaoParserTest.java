package com.metaui.eshop.api.taobao;

import com.metaui.eshop.api.domain.Category;
import org.junit.Test;

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

}