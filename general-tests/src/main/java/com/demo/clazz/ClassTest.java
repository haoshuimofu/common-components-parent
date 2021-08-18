package com.demo.clazz;

/**
 * @author wude
 * @date 2021/6/1 14:22
 */
public class ClassTest {

    public static void main(String[] args) {
        // 不初始化不会运营类中的
        System.err.println(SubClass.value);
//        System.err.println(new SuperClass());
//        System.err.println(new SubClass());
    }

}