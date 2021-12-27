package com.demo.jdk8;


import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dewu.de
 * @date 2021-12-16 2:38 下午
 */
public class CompletableFutureDemo {

    // 计算OD对距离线程池
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            16, 16,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder()
                    .namingPattern("od-distance-%s").build());

    private static List<String> query(List<String> taskIds) throws ExecutionException, InterruptedException, TimeoutException {
        List<String> resultList = new ArrayList<>();

        List<CompletableFuture<String>> cfList = taskIds.stream().map(taskId -> {
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                for (int i = 0; i < 100000; i++) {

                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                resultList.add(taskId);
//                System.out.println("task=" + taskId + "执行了 at " + System.currentTimeMillis());
                return taskId;
            }, THREAD_POOL)
//                    .handle(new BiFunction<String, Throwable, String>() {
//                        @Override
//                        public String apply(String s, Throwable throwable) {
//
//                            if (throwable == null) {
//                                System.out.println("task=" + taskId + "完成! result=" + s + ", at " + System.currentTimeMillis());
//                                resultList.add(taskId);
//                            } else {
//                                System.err.println("task=" + taskId + "异常! e=" + throwable + ", at " + System.currentTimeMillis());
//                            }
//                            return "";
//                        }
//                    })
                    ;
//            cf.whenComplete((s, throwable) -> {
//                if (throwable == null) {
//                    System.out.println("task=" + taskId + "完成! result=" + s + ", at " + System.currentTimeMillis());
//                } else {
//                    System.err.println("task=" + taskId + "异常! e=" + throwable + ", at " + System.currentTimeMillis());
//                }
//            });
//            cf.thenApply(s -> {
//                resultList.add(taskId);
//                return s;
//            });
            return cf;
        }).collect(Collectors.toList());

        CompletableFuture<List<String>> resultTask = CompletableFuture.allOf(cfList.toArray(new CompletableFuture[]{}))
                .thenApply(s -> cfList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        try {
            resultList = resultTask.get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> finalResultList = resultList;
        List<String> diff = taskIds.stream().filter(e -> !finalResultList.contains(e)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(diff)) {
            System.out.println("task=" + JSON.toJSONString(taskIds)
                    + ", result=" + JSON.toJSONString(resultList)
                    + ", diff=" + JSON.toJSONString(diff)
                    + ", at " + System.currentTimeMillis());
        } else {
            System.err.println("task=" + JSON.toJSONString(taskIds)
                    + ", result=" + JSON.toJSONString(resultList)
                    + ", diff=" + JSON.toJSONString(diff)
                    + ", at " + System.currentTimeMillis());
        }

        return resultList;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        long begin = System.currentTimeMillis();
        // 2. 提交任务
        int batch = 100;
        int total = 0;
        for (int i = 0; i < batch; i++) {
            int size = (int) (Math.random() * 10) + 1;
            List<String> taskIds = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                taskIds.add(i + "-" + j);
            }
            List<String> resultList = query(taskIds);
            total += size;


        }
        System.err.println("total=" + total);

        total = 0;
        int skip = batch;
        for (int i = 0; i < batch; i++) {
            int size = (int) (Math.random() * 10) + 1;
            List<String> taskIds = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                taskIds.add((skip + i) + "-" + j);
            }
            List<String> resultList = query(taskIds);
            total += size;
        }
        long end = System.currentTimeMillis();
        System.err.println("耗时 = " + (end - begin));


    }


    public static Map<String, String> test() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Map<String, String> data = new HashMap<>(3);
        //第一个任务。
        CompletableFuture<String> task01 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task01 by " + Thread.currentThread().getName());
            return "task01";
        }, THREAD_POOL);
        //第二个任务。
        CompletableFuture<String> task02 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task02 by " + Thread.currentThread().getName());
            return "task02";
        }, THREAD_POOL);
        // 第三个任务
        CompletableFuture<String> task03 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task03 by " + Thread.currentThread().getName());
            return "task03";
        }, THREAD_POOL);
        // allOf返回一个新的CompletableFuture,
        CompletableFuture.allOf(task01, task02, task03).whenComplete((v, t) -> {
            System.err.println(v + " by " + Thread.currentThread().getName());
        }).get();
        long tempEnd = System.currentTimeMillis();
        System.err.println("总耗时: " + (tempEnd - start));

        // get()方法会阻塞
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        data.put("task01", task01.get());
        System.out.printf("task01执行完毕;当前时间:%s\n", formatter.format(LocalDateTime.now()));
        data.put("task02", task02.get());
        System.out.printf("task02执行完毕;当前时间:%s\n", formatter.format(LocalDateTime.now()));
        data.put("task03", task03.get());
        System.out.printf("task03执行完毕;当前时间:%s\n", formatter.format(LocalDateTime.now()));

        long end = System.currentTimeMillis();
        System.err.println("总耗时: " + (end - start));
        return data;
    }


}
