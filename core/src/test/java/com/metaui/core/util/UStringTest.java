package com.metaui.core.util;

import org.junit.Test;

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
}