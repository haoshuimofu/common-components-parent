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

        // 12,34,56
        // 135,246
        int[][] a1 = new int[3][2];
        a1[0] = new int[]{1, 2};
        a1[1] = new int[]{3, 4};
        a1[2] = new int[]{5, 6};

        int[][] a2 = new int[2][3];
        a2[0] = new int[]{1, 3, 5};
        a2[1] = new int[]{2, 4, 6};

        System.out.println(ObjectSizeCalculator.getObjectSize(a1) + "-" + ObjectSizeCalculator.getObjectSize(a2));
        System.out.println(RamUsageEstimator.sizeOf(a1) + "-" + RamUsageEstimator.sizeOf(a2));

        UserVo u1 = new UserVo();
        UserVo u2 = new UserVo();
        UserVo u3 = new UserVo();
        UserVo u4 = new UserVo();
        UserVo u5 = new UserVo();
        UserVo u6 = new UserVo();
        UserVo[][] uv1 = new UserVo[3][2];
        uv1[0] = new UserVo[]{u1, u2};
        uv1[1] = new UserVo[]{u3, u4};
        uv1[2] = new UserVo[]{u5, u6};

        UserVo[][] uv2 = new UserVo[2][3];
        uv2[0] = new UserVo[]{u1, u3, u5};
        uv2[1] = new UserVo[]{u2, u4, u6};
        System.out.println(ObjectSizeCalculator.getObjectSize(uv1) + "-" + ObjectSizeCalculator.getObjectSize(uv2));
        System.out.println(RamUsageEstimator.sizeOf(uv1) + "-" + RamUsageEstimator.sizeOf(uv2));


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
