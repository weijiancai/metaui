package com.metaui.core.util;

/**
 * 数字工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class UNumber {
    public static int toInt(Object object) {
        return toInt(object, 0);
    }

    public static int toInt(Object object, int defaultValue) {
        try {
            return object == null ? defaultValue : Integer.parseInt(object.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toDouble(Object obj) {
        try {
            String str = UString.toString(obj);
            return UString.isEmpty(str) ? 0 : Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long toLong(String str) {
        try {
            return UString.isEmpty(str) ? 0 : Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
