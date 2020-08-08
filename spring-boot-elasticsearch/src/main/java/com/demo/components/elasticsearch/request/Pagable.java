package com.demo.components.elasticsearch.request;

import java.util.List;
import java.util.TreeMap;

/**
 * @author wude
 * @date 2020/8/8 18:40
 */
public class Pagable {

    private int from;
    private int size;
    private List<String> fields;
    private TreeMap<String, String> sort;

}