package com.demo.collection;

/**
 * @author wude
 * @date 2020/11/5 10:47
 */
public class Test {

    public static void main(String[] args) {

        // & 当两边都是boolean表达式时表示逻辑and运算符, 无论第一个条件是true or false, 第二个条件都是执行
        // 虽然第二个条件怎么样都会执行, 但实际的最终判断结果true or false还是有两个条件共同决定的
        int i = 0;
        if (10 == 10 & (i++) != 0) {
            System.out.println("结果为真" + i);
        } else {
            System.out.println("结果为假" + i);
        }
        // print result: 结果为真1

        if (10 == 11 & (i++) != 1) {
            System.out.println("结果为真" + i);
        } else {
            System.out.println("结果为假" + i);
        }
        // print result: 结果为假1

        // | 当两边都是boolean表达式时表示逻辑and运算符, 无论第一个条件是true or false, 第二个条件都是执行
        // 虽然第二个条件怎么样都会执行, 但实际的最终判断结果true or false还是有两个条件共同决定的
        i = 0;
        if (10 == 10 | (i++) != 1) {
            System.out.println("结果为真" + i);
        } else {
            System.out.println("结果为假" + i);
        }
        // print result: 结果为真1

        if (10 == 11 | (i++) != 1) {
            System.out.println("结果为真" + i);
        } else {
            System.out.println("结果为假" + i);
        }
        // print result: 结果为假1

    }

}