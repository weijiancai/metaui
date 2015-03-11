package com.metaui.core.setting;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PreferenceSettingsTest {
    @Test
    public void testObject() {
        PreferenceSettings settings = PreferenceSettings.getInstance();
        List<String> list = new ArrayList<>();
        list.add("weijiancai");
        list.add("1233");
        settings.put("testList", list);

        List<String> result = settings.getObject("testList");
        for (String str : result) {
            System.out.println(str);
        }
    }
}