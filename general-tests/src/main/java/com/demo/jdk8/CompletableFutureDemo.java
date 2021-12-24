package com.demo.jdk8;


import com.alibaba.fastjson.JSON;
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

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            8, 8,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder()
                    .namingPattern("completable-future-%s").build());

    public static void main(String[] args) throws Exception {
        int batch = 10;
        for (int i = 0; i < batch; i++) {
            int size = new Random().nextInt(10) + 1;
            List<String> taskIds = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                taskIds.add(i + "-" + j);
            }
            List<String> result = batchTask(taskIds);
        }

//        test();

    }

    /**
     * 模拟任务执行, 将taskId直接返回
     *
     * @param taskIds
     * @return
     */
    private static List<String> batchTask(List<String> taskIds) {
        List<String> result = new ArrayList<>();
        Stream<CompletableFuture<String>> stream = taskIds.stream().map(taskId -> {
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(taskIds.size() * 200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // result.add(taskId);
                return taskId;
            }).handle((res ,throwable) -> {
                if (throwable != null) {
                    System.err.println(taskId + "执行异常!");
                } else {
//                    System.out.println(res + " adding...");
//                    result.add(res);
                }
                return res;
            });
            return cf;
        });
        List<CompletableFuture<String>> cfs = stream.collect(Collectors.toList());

        // CompletableFuture.allOf(cfs.toArray(new CompletableFuture[]{})).join();


//        for (CompletableFuture<String> cf : cfs) {
//            try {
//                result.add(cf.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

         CompletableFuture<List<String>> allCf = CompletableFuture.allOf(cfs.toArray(new CompletableFuture[]{}))
                .thenApply(ignore -> cfs.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
         result = allCf.join();

        List<String> finalResult = result;
        List<String> diff = taskIds.stream().filter(e -> !finalResult.contains(e)).collect(Collectors.toList());
        if (diff.isEmpty()) {
            System.out.println("task=" + JSON.toJSONString(taskIds)
                    + ", result=" + JSON.toJSONString(result)
                    + ", diff=" + JSON.toJSONString(diff)
                    + ", at " + System.currentTimeMillis());
        } else {
            System.err.println("task=" + JSON.toJSONString(taskIds)
                    + ", result=" + JSON.toJSONString(result)
                    + ", diff=" + JSON.toJSONString(diff)
                    + ", at " + System.currentTimeMillis());
        }
        return result;
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
