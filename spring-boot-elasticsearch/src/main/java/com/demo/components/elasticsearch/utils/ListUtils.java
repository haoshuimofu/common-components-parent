package com.demo.components.elasticsearch.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wude
 * @date 2020/6/9 16:26
 */
public class ListUtils {

    public static List<String> removeBlankElements(List<String> elements) {
        return CollectionUtils.isEmpty(elements) ? elements : elements.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }

    public static List<String> removeEmptyElements(List<String> elements) {
        return CollectionUtils.isEmpty(elements) ? elements : elements.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }

    public static <T> List<T> removeNullElements(List<T> elements) {
        return CollectionUtils.isEmpty(elements) ? elements : elements.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T> List<T> merge(List<List<T>> lists) {
        if (lists == null) return null;
        if (lists.isEmpty()) return new ArrayList<>();
        List<T> list = new ArrayList<>();
        for (List<T> subList : lists) {
            if (subList != null && !subList.isEmpty()) {
                list.addAll(subList);
            }
        }
        return list;
    }
}