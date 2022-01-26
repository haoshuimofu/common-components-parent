package com.demo.jdk8;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author dewu.de
 * @date 2022-01-12 3:33 下午
 */
public class Test {

    private static final Integer THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2;
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            1, 1,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder()
                    .namingPattern("od-distance-%s").build());

    public static void main(String[] args) throws InterruptedException {
        Future<Integer> future = THREAD_POOL.submit(() -> returnException());
        try {
            System.out.println(future.get(3, TimeUnit.SECONDS) + " -> 1");
        } catch (Exception e) {
            System.err.println("error1");
        }
        try {
            System.out.println(future.get(3, TimeUnit.SECONDS) + " -> 2");
        } catch (Exception e) {
            System.err.println("error2");
        }

        Thread.sleep(5000);

        try {
            System.out.println(future.get(3, TimeUnit.SECONDS) + " -> 3");
            System.out.println("success");
        } catch (Exception e) {
            System.err.println("error3");
        }
    }

    public static int returnException() {
        try {
            Thread.sleep(10000);
            return 1;
        } catch (InterruptedException e) {
            System.err.println("error");
            return 0;
        }
    }


}
