package com.demo.clazz;

/**
 * @author wude
 * @date 2021/6/1 14:24
 */
public class SubClass extends SuperClass {

    static {
        System.err.println("SubClass static!");
    }

    public SubClass() {
        System.err.println("SubClass init!");
    }
}