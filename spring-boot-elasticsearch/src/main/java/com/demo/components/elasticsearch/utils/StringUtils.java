package com.demo.components.elasticsearch.utils;

/**
 * @author wude
 * @date 2020/6/15 17:56
 */
public class StringUtils {

    private static final String EMPTY = "";
    private static final String BLANK = " ";

    public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String trimToNull(String str) {
        if (str != null) {
            str = str.trim();
            if (EMPTY.equals(str)) {
                return null;
            }
        }
        return null;
    }

}