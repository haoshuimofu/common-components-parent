package com.demo.jdk8;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author dewu.de
 * @date 2021-12-24 2:23 下午
 */
public class CompletableFutureDemo1 {

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            8, 8,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder()
                    .namingPattern("completable-future-%s").build());

    public static void main(String[] args) throws Exception {
        int count = 3;
        ArrayList<Integer> nums = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            nums.add(i);
        }
        // 3. 回调任务执行结果
        CompletableFuture[] cfs = nums.stream().map((num) -> {
            CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
                return getDoubleVal(num);
            }, THREAD_POOL);
            // 异步返回执行结果, 使用ForkJoinPool把申请交给另外一个线程，如果不带async就由当前线程继续执行
            // complete-完成、结束, 正常执行结束返回正常值, 异常则返回null
            cf.whenComplete((result, exception) -> {
                System.out.println(num + " 计算结果是: " + result + " by whenComplete! thread=[" + Thread.currentThread().getName() + "]");
            });
            cf.thenApply(s -> {
                int ns = s + s / 2;
                System.out.println(num + " 变形得到: " + ns + " by thenApply! thread=[" + Thread.currentThread().getName() + "]");
                return ns;
            }).thenApply(s -> {
                int ns = s + 1;
                System.out.println(num + " 变形得到: " + ns + " by thenApply! thread=[" + Thread.currentThread().getName() + "]");
                return ns;
            });
            // 将处理结果传递到子任务
            cf.thenAccept((result) -> {
                System.out.println(num + " 计算结果是: " + result + " by thenAccept! thread=[" + Thread.currentThread().getName() + "]");
            });
            return cf;
        }).toArray(CompletableFuture[]::new);

        // 获取最先执行完的任务
        CompletableFuture<Object> firstEnd = CompletableFuture.anyOf(cfs);
        System.out.println("最先执行完的任务：" + firstEnd.get());

        // 等待所有任务执行完
        CompletableFuture.allOf(cfs).join();
        for (CompletableFuture cf : cfs) {
            System.err.println(cf.get());
        }

    }

    public static int getDoubleVal(int i) {
        System.out.println(i + " 正在计算中..." + Thread.currentThread().getName());

        try {
            if (i == 2) {
                Thread.sleep(1000);
//                throw new RuntimeException("2计算时出错了!");
            }
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i * 2;

    }

}
