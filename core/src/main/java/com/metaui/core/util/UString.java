package com.metaui.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串处理工具类
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class UString {
    public static final String EMPTY = "";

    public static String isEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 将数据库的表名，转换为类名，例如sys_dbms_define,转换后的结果是DbmsDefine
     *
     * @param tableName 数据库表名
     * @return 返回类名
     */
    public static String tableNameToClassName(String tableName) {
        tableName = tableName.toLowerCase();
        if (tableName.startsWith("mu_db_") || tableName.startsWith("mu_dz") || tableName.startsWith("mu_pm")) {
            tableName = tableName.substring(6);
        }
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String str : tableName.split("_")) {
            if (i++ == 0 && tableName.startsWith("mu_")) {
                continue;
            }

            result.append(firstCharToUpper(str.toLowerCase()));
        }

        return result.toString();
    }

    public static String columnNameToFieldName(String columnName) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String str : columnName.split("_")) {
            if (i == 0) {
                result.append(firstCharToLower(str.toLowerCase()));
            } else {
                result.append(firstCharToUpper(str.toLowerCase()));
            }
            i++;
        }

        return result.toString();
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 返回首字母大写的字符串
     */
    public static String firstCharToUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 返回首字母小写的字符串
     */
    public static String firstCharToLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 获取字符串的非Null值，如果为null，返回“”
     *
     * @param str 原始字符串
     * @param defaultStr 默认值
     * @return 如果str非Null，返回str，否则返回defaultStr，如果defaultStr为Null，则返回“”
     */
    public static String getNotNull(String str, String defaultStr) {
        return isEmpty(str) ? (isEmpty(defaultStr) ? "" : defaultStr) : str;
    }

    /**
     * 将字符串转化为boolean值
     *
     * @param str 字符串
     * @return 如果str等于"T"或者"Y"或者"true"，则返回true，否则返回false
     */
    public static boolean toBoolean(String str) {
        return !isEmpty(str) && ("T".equalsIgnoreCase(str) || "Y".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str));
    }

    public static String substringByByte(String str, int i, int length) {
        return str.substring(i, i + length);
    }

    /**
     * 字符串左边填充n个字符c
     *
     * @param str 字符串
     * @param c 要填充的字符
     * @param length 字符窜总长度
     * @return 返回填充后的字符
     */
    public static String leftPadding(String str, char c, int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() + str.length() < length) {
            sb.append(c);
        }
        return sb.toString() + str;
    }

    /**
     * 去掉两边的空白符，包括中文空白符" "
     *
     * @param str 字符串
     * @return 返回去掉空白符后的字符串
     */
    public static String trim(String str) {
        if(str == null) return null;

        str = str.trim();

        while (str.length() > 0 && Character.isSpaceChar(str.charAt(0))) {
            str = str.substring(1);
        }

        while (str.startsWith("　")) {
            str = str.substring(1);
        }

        while (str.length() > 0 && Character.isSpaceChar(str.charAt(str.length() - 1))) {
            str = str.substring(0, str.length() - 1);
        }

        while (str.endsWith("　")) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    /**
     * 取字符串中数字和小数点
     *
     * @param str 字符串
     * @return 返回数字字符串
     */
    public static String getNumber(String str) {
        if(UString.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()) {
            if((c >= '0' && c <= '9') || c == '.') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 判读字符串是否在字符串数组中
     *
     * @param array 字符串数据
     * @param str 字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否在数组中
     * @since 1.0.0
     */
    public static boolean inArray(String[] array, String str, boolean isIgnoreCase) {
        if (isIgnoreCase) {
            for (String s : array) {
                if (s.equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        } else {
            return Arrays.binarySearch(array, str) >= 0;
        }
    }

    /**
     * 将字符串List转化为逗号分隔的字符串
     *
     * @param list 需要转化的字符串List
     * @return 返回逗号分隔的字符串
     * @since 1.0.0
     */
    public static String convert(List list) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : list) {
            sb.append(UObject.toString(obj)).append(",");
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 按分隔符分隔字符串，取最后一个字符串
     *
     * @param str 要解析的字符串
     * @param splitChar 分隔符
     * @return 返回最后一个字符串
     * @since 1.0.0
     */
    public static String getLastName(String str, String splitChar) {
        if (str.endsWith(splitChar)) {
            str = str.substring(0, str.length() - splitChar.length());
        }
        String[] strs = str.split(splitChar);
        return strs[strs.length - 1];
    }

    /**
     * 按指定的分隔符，拆分字符串，例如字符串com/metaui/core/ui/Test.java
     * 拆分结果为：['com', 'com/metaui', 'com/metaui/core', 'com/metaui/core/ui/', 'com/metaui/core/ui/Test.java']
     *
     * @param str 要拆分的字符串
     * @param splitChar 分隔符
     * @return 返回拆分后的字符串数组
     */
    public static List<String> splitString(String str, String splitChar) {
        List<String> result = new ArrayList<String>();
        String[] strs = str.split(splitChar);
        for (int i = 1; i <= strs.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(strs[j]);
                if(j < i - 1) {
                    sb.append(splitChar);
                }
            }
            result.add(sb.toString());
        }
        return result;
    }

    /**
     * 获得字符串值，如果值为空，则返回默认值
     *
     * @param value 字符串值
     * @param defaultValue 默认值
     * @return 如果值为空，则返回默认值
     * @since 1.0.0
     */
    public static String getValue(String value, String defaultValue) {
        return UString.isEmpty(value) ? defaultValue : value;
    }

    /**
     * 删除末尾字符串
     *
     * @param sb
     * @param endStr
     */
    public static void deleteEndStr(StringBuilder sb, String endStr) {
        int start = sb.length() - endStr.length();
        if (endStr.equals(sb.substring(start))) {
            sb.delete(start, sb.length());
        }
    }
}
