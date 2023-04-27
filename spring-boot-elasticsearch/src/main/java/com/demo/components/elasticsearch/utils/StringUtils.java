package com.demo.components.elasticsearch.utils;

import java.util.Collection;

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
            return str;
        }
        return null;
    }

    public static String join(Collection<String> collection, String splitChar) {
        if (CollectionUtils.isEmpty(collection)) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (String str : collection) {
            sb.append(str);
            if (index != collection.size() - 1) {
                sb.append(splitChar);
            }
        }
        return sb.toString();
    }

}