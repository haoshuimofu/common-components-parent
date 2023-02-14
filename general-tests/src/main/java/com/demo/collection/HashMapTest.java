package com.demo.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-02-14 1:52 下午
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "1");
        System.out.println(map.size());
    }
}
