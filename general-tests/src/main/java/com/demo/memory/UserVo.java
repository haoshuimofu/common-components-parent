package com.demo.memory;

import com.carrotsearch.sizeof.RamUsageEstimator;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

/**
 * @author dewu.de
 * @date 2021-12-22 10:44 上午
 */
public class UserVo {

    private String name;//4
    private int id;//4
    private double money;//8
    private byte health;//1
    private boolean isGirl;//1
    private short age;//2

    public static void main(String[] args) {
        carrotsearch();

        getObjectSize();
    }

    private static long carrotsearch() {
        UserVo userVo = new UserVo();

        long size = RamUsageEstimator.sizeOf(userVo);
        //32 = 8 +4+4(padding) +8 +4+2+1+1
        //name为null，故没有值
        System.out.println("carrotsearch memory is: " + size);
        return size;
    }

    private static long getObjectSize() {
        UserVo userVo = new UserVo();

        long start = System.currentTimeMillis();
        long size = ObjectSizeCalculator.getObjectSize(userVo);
        long end = System.currentTimeMillis() - start;
        //耗时： 14 ms
        System.out.println("耗时： " + end + " ms");
        //32
        System.out.println("getObjectSize memory is: " + size);

        return size;
    }

}
