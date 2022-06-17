package com.demo.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author dewu.de
 * @date 2022-06-17 11:40 上午
 */
public class CompletableFutureJoinDemo {


    public static void main(String[] args) {

        List<Integer> seconds = new ArrayList<>();
        List<Integer> times = Arrays.asList(100, 200, 300, 500, 800);
        long start = System.currentTimeMillis();
        List<CompletableFuture<Boolean>> futures = times.stream()
                .map(second -> CompletableFuture.supplyAsync(() -> action(second))
                        .thenApply(result -> seconds.add(result)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{})).join();
        long interval = System.currentTimeMillis() - start;
        System.out.println("总耗时 = " + interval);

    }


    private static int action(int seconds) {
        try {
            Thread.sleep(seconds);
            System.out.println("Thread(" + Thread.currentThread().getName() + ") has sleep " + seconds + " second!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return seconds;
    }

}
