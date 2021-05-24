package com.demo.jvm;

/**
 * 在VM Options添加参数[-XX:+PrintGCDetails]以便打印GC Detail
 *
 * @author wude
 * @date 2021/5/17 19:57
 */
public class ReferenceCountingGC {

    private static final int _1MB = 1024 * 1024;
    private Object instance = null;
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {

        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();

    }

    public static void main(String[] args) {
        testGC();
    }

}