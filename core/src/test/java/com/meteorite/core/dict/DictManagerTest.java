package com.meteorite.core.dict;

import com.metaui.core.datasource.db.DatabaseType;
import com.metaui.core.dict.DictCategory;
import com.metaui.core.dict.DictManager;
import com.metaui.core.meta.DisplayStyle;
import org.junit.Test;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DictManagerTest {
    @Test
    public void testAddDict() throws Exception {
        DictManager.addDict(DatabaseType.class);
    }

    @Test
    public void testAddEnumDict() {
        DictCategory dict = DictManager.getDict(DisplayStyle.class);
        System.out.println(dict);
    }
}
