package com.demo.jdk8;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dewu.de
 * @date 2021-12-23 3:42 下午
 */
public class Demo {

    private static final ExecutorService OD_DISTANCE_THREAD_POOL = new ThreadPoolExecutor(
            12, 12,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder()
                    .namingPattern("od-distance-%s").build());

    private static List<String> query(List<String> taskIds) throws ExecutionException, InterruptedException, TimeoutException {
        List<String> resultList = new ArrayList<>(taskIds.size());

        Stream<CompletableFuture<String>> cfStream = taskIds.stream().map(taskId -> {
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                for (int i = 0; i < 100000; i++) {

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                resultList.add(taskId);
                System.out.println("task=" + taskId + "执行了 at " + System.currentTimeMillis());
                return taskId + " finish";
            }, OD_DISTANCE_THREAD_POOL)
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
            cf.whenComplete((s, throwable) -> {
                if (throwable == null) {
                    System.out.println("task=" + taskId + "完成! result=" + s + ", at " + System.currentTimeMillis());
//                    resultList.add(taskId);
                } else {
                    System.err.println("task=" + taskId + "异常! e=" + throwable + ", at " + System.currentTimeMillis());
                }
            });
            cf.thenApply(s -> {
                resultList.add(taskId);
                return s;
            });
            return cf;
        });
        CompletableFuture[] cfs = cfStream.toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(cfs).join();

        List<String> diff = taskIds.stream().filter(e -> !resultList.contains(e)).collect(Collectors.toList());
        if (diff.isEmpty()) {
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
        // 2. 提交任务
        int batch = 10;
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
        batch = 10;
        System.err.println("now is " + System.currentTimeMillis());
        Thread.sleep(15000);
        System.err.println("now is " + System.currentTimeMillis());
        for (int i = 0; i < batch; i++) {
            int size = (int) (Math.random() * 10) + 1;
            List<String> taskIds = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                taskIds.add((skip + i) + "-" + j);
            }
            List<String> resultList = query(taskIds);
            total += size;


        }


    }

}
