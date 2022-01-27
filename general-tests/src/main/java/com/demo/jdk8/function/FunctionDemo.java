package com.demo.jdk8.function;

import java.util.function.Function;

/**
 * @author dewu.de
 * @date 2022-01-26 8:36 下午
 */
public class FunctionDemo {

    public static void main(String[] args) {
        Function<Integer, String> f = t -> t + "";
        System.out.println(f.apply(5));

    }


}
