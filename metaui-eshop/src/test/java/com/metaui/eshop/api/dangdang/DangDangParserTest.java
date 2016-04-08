package com.metaui.eshop.api.dangdang;

import com.metaui.eshop.api.ApiCategory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0
 */
public class DangDangParserTest {
    @Test
    public void parse() throws Exception {
        DangDangParser parser = new DangDangParser();
        List<ApiCategory> list = parser.parse();
        for (ApiCategory category : list) {
            System.out.println(category.getName() + "  --> " + category.getDesc());
        }
    }

}