package com.meteorite.core.parser.book;

import com.metaui.core.parser.book.IWebProduct;
import com.metaui.core.parser.book.JingDongParser;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class JingDongParserTest {
    @Test public void test() {
        JingDongParser parser = new JingDongParser("9787010129860");
        List<IWebProduct> list = parser.parse();
        MatcherAssert.assertThat(list.size(), equalTo(1));
        System.out.println(list.get(0));
        IWebProduct product = list.get(0);
        assertThat(product.getName(), equalTo(""));
    }
}
