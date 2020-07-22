package com.demo.components.elasticsearch.utils;

import java.util.Collection;

/**
 * @author wude
 * @date 2020/6/19 14:30
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

}