package com.demo.clazz;

import java.util.*;

/**
 * @author dewu.de
 * @date 2021-12-31 11:25 上午
 */
public class MapTest {

    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>();
//        for (int i = 0; i < 100; i++) {
//            int random = new Random().nextInt(100) + 1;
//            map.put(random, String.valueOf(random));
//        }
//


        map.put(4, "4");
        map.put(3, "3");
        map.put(2, "2");
        map.put(1, "1");
        map.put(5, "5");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        List<String> list = new ArrayList<>(map.values());
        for (String s : list) {
            System.out.print(s + " ");
        }

    }
}
