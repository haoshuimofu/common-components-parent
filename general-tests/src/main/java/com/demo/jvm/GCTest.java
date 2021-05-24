package com.demo.jvm;

/**
 * VM Options: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * <p>
 *     [GC (Allocation Failure) [PSYoungGen: 8192K->840K(9216K)] 12288K->4944K(19456K), 0.0009936 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Heap
 *  PSYoungGen      total 9216K, used 1087K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 8192K, 3% used [0x00000000ff600000,0x00000000ff63df28,0x00000000ffe00000)
 *   from space 1024K, 82% used [0x00000000ffe00000,0x00000000ffed2020,0x00000000fff00000)
 *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 *  ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002010,0x00000000ff600000)
 *  Metaspace       used 3131K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 341K, capacity 388K, committed 512K, reserved 1048576K
 * </p>
 *
 * @author wude
 * @date 2021/5/24 18:45
 */
public class GCTest {

    private static final int _1MB = 1024 * 1024;


    public static void main(String[] args) {
        testAllocation();
    }

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; //出现一次Minor GC
    }

}