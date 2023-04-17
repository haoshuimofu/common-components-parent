package com.demo.components.desensitive.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-04-14 2:53 下午
 */
public class DesensitizedCommonUtils {

    public static boolean isNotEmptyString(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isNotEmptyCollection(Collection<?> c) {
        return c != null && c.size() > 0;
    }

    public static boolean isNotEmptyMap(Map<?, ?> m) {
        return m != null && m.size() > 0;
    }

}
