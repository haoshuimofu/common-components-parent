package com.demo.clazz;

/**
 * @author wude
 * @date 2021/6/1 14:23
 */
public class SuperClass {

    static {
        System.err.println("SuperClass static!");
    }

    public static final int value = 123;

    public SuperClass() {
        System.err.println("SuperClass init!");
    }
}