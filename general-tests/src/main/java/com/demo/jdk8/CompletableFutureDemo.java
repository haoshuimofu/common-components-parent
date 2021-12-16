package com.demo.jdk8;


import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dewu.de
 * @date 2021-12-16 2:38 下午
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {
        // 1. 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 2. 提交任务
        int taskSize = 100;
        ArrayList<Integer> taskIds = new ArrayList<>(taskSize);
        for (int i = 0; i < taskSize; i++) {
            taskIds.add(i + 1);
        }
        // 3. 回调任务执行结果
        CompletableFuture[] cfs = taskIds.stream().map((taskId) -> {
            String threadName = Thread.currentThread().getName();
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
                return "任务 " + taskId + " 执行完成! thread=[" + Thread.currentThread().getName() + "]";
            }, executorService);
            // 异步返回执行结果,使用ForkJoinPool把申请交给另外一个线程，如果不带async就由当前线程继续执行
            completableFuture.whenCompleteAsync((result, exception) -> {
                System.err.println("任务 " + taskId + " whenCompleteAsync! thread=[" + Thread.currentThread().getName() + "]");
            });
            // 将处理结果传递到子任务
            completableFuture.thenAccept((result) -> {
                System.err.println("任务 " + taskId + " thenAccept! thread=[" + Thread.currentThread().getName() + "]");
            });
            return completableFuture;
        }).toArray(CompletableFuture[]::new);

        // 获取最先执行完的任务
        CompletableFuture<Object> firstEnd = CompletableFuture.anyOf(cfs);
        System.out.println("最先执行完的任务：" + firstEnd.get());
        executorService.shutdown();
    }

}
