package com.demo.juc.completablefuture;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.List;

/**
 * @author dewu.de
 * @date 2022-01-28 12:02 下午
 */
public class CompletableFutureDemo {


    public static void main(String[] args) {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 1 / 0;
            return 3;
        });


        try {
            CompletableFuture.allOf(future1, future2, future3).get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            // allOf生成一个新的CompletableFuture, 如果有一个future未complete就TimeoutException, 但后面仍然可以future.get结果
            // 如果future其中有异常, 这里也抛出异常, 不影响后面future.get
        }
        List<Integer> values = new ArrayList<>();
        long begin = System.currentTimeMillis();
        try {
            values.add(future1.get());
            values.add(future2.get());
            values.add(future3.get());
            System.out.println("time=" + (System.currentTimeMillis() - begin));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Integer value : values) {
            System.out.print(value + " ");
        }
    }

}
