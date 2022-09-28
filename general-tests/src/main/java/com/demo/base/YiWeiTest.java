package com.demo.base;

/**
 * Java以为运算符测试
 *
 * @author dewu.de
 * @date 2022-06-23 3:34 下午
 */
public class YiWeiTest {

    public static void main(String[] args) {
        int num = -8;
        System.out.println(num << 2);
        System.out.println((num << 2) >> 2);

        System.out.println(8|3);
        System.out.println(8&3);

        System.out.println(Integer.toBinaryString(8));

    }

}
