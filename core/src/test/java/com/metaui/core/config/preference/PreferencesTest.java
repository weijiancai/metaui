package com.metaui.core.config.preference;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class PreferencesTest {
    @Test
    public void testPut() {
        Preferences.getInstance().put("Test", "123");
        Preferences.getInstance().put("Test1", "456");
    }
}