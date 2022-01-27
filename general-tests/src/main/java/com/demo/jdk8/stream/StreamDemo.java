package com.demo.jdk8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dewu.de
 * @date 2022-01-27 11:49 上午
 */
public class StreamDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("5");
        list.add("4");
        list.add("1");
        // int类型智能转int[]
        int[] a = list.stream().mapToInt(value -> Integer.parseInt(value) + 1).toArray();
        for (int i : a) {
            System.out.print(i + " ");
        }

        System.out.println();
        // int类型包装类Integer转List
        List<Integer> iList = list.stream().mapToInt(value -> Integer.parseInt(value) + 2).boxed().collect(Collectors.toList());
        for (Integer i : iList) {
            System.out.print(i + " ");
        }

        System.out.println();
        double[] dList = list.stream().mapToDouble(value -> Double.parseDouble(value) / 100.0).toArray();
        for (double v : dList) {
            System.out.print(v + " ");
        }

        System.out.println();
        List<Integer> flList = list.stream().flatMap(value -> list.stream().map(e-> Integer.parseInt(e)*10).parallel()).collect(Collectors.toList());
        for (Integer s : flList) {
            System.out.print(s + " ");
        }

    }

}
