package com.metaui.core.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UStringTest {

    @Test
    public void testDeleteEndStr() throws Exception {
        StringBuilder sb = new StringBuilder("SELECT A,B,");
        UString.deleteEndStr(sb, ",");
        assertThat(sb.toString(), equalTo("SELECT A,B"));
    }

    @Test
    public void testConvert() {
        List<String> list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        String result = UString.convert(list, ",");
        assertThat(result, equalTo("张三,李四,王五"));
        result = UString.convert(list, " OR ");
        assertThat(result, equalTo("张三 OR 李四 OR 王五"));

    }
}