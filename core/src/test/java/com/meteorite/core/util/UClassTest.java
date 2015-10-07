package com.meteorite.core.util;

import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.util.UClass;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UClassTest {
    @Test
    public void testToString() throws Exception {
        System.out.println(UClass.toString(DatabaseType.class));
    }
}
