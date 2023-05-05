package com.demo.base;

/**
 * @author dewu.de
 * @date 2023-04-27 5:21 下午
 */
public class HexTest {

    public static void main(String[] args) {
        int n1 = 10;
        int n2 = 12;

        System.out.println(Integer.parseInt("1010", 2));
        System.out.println(Integer.parseInt("1010", 10));

        int num = 0B1010;
        int num1 = 0B1011;
        System.out.println(num & num1);
        System.out.println(num | num1);


        System.out.println(Integer.toBinaryString(Integer.valueOf("-1", 2)));
        System.out.println(Integer.toHexString(Integer.valueOf("-1", 2)));

    }

}
