package com.demo.jdk8;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author dewu.de
 * @date 2022-01-20 10:51 上午
 */
public class FutureTest {


    public static void main(String[] args) {
        List<String> results = new ArrayList<>();

        CompletableFuture<Boolean> future100 = CompletableFuture.supplyAsync(() -> sleep100(results));

        CompletableFuture<Boolean> future200 = CompletableFuture.supplyAsync(() -> sleep200(results));
        long begin = System.currentTimeMillis();
        try {
            // 最长阻塞等待时间
            CompletableFuture.allOf(future100, future200).get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
        long end = System.currentTimeMillis();
        System.out.println(JSON.toJSONString(results));
        System.out.println("耗时=" + (end - begin));


    }

    public static boolean sleep100(List<String> results) {
        try {
            Thread.sleep(10);
            results.add("100");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sleep200(List<String> results) {
        try {
            Thread.sleep(20);
            results.add("200");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
