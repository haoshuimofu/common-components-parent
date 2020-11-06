package com.demo.collection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wude
 * @date 2020/11/4 10:10
 */
public class CollectionTest {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        list.add("1"); // 第一次添加元素对象, 发现elementData是EMPTY_ARRAY, 初始化容量10
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        // 添加第11个元素时, 初始化容量10已经无法满足
        // int newCapacity = oldCapacity + (oldCapacity >> 1), 即 15 = 10 + (10 >> 1)
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        // 添加第16个元素时, 容量15已经无法满足
        // int newCapacity = oldCapacity + (oldCapacity >> 1), 即 22 = 15 + (15 >> 1)
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");

//        list.forEach(System.err::println);
//        System.err.println(11 >> 1);
//
//        for (int i = 0; i < 10000; i++) {
//            System.err.println(i >> 1);
//        }

        map();

    }

    public static void map() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
    }

}