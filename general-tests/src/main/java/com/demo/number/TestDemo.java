package com.demo.number;

/**
 * @author dewu.de
 * @date 2022-06-21 11:09 上午
 */
public class TestDemo {

    static int factor = 200;


    public static void main(String[] args) {
        double lng = 112.6382466;
//        System.out.println(2.0d-1.1d);
        System.out.println(hsf(lng, factor));
        lng = 112.6332466;
//        System.out.println(2.0d-1.1d);
        System.out.println(hsf(lng, factor));

    }

    /**
     * @param d
     * @return
     */
    public static double hsf(double d, double factor) {
        System.out.println("d = " + d);
        System.out.println("d * factor = " + (d * factor));
        System.out.println("Math.round(d * factor) = " + Math.round(d * factor));
        System.out.println("Math.round(d * factor) / factor = " + Math.round(d * factor) / factor);
        return Math.round(d * factor) / factor;
    }
}
