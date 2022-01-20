package com.demo.jdk8;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author dewu.de
 * @date 2021-12-31 3:25 下午
 */
public class ThreadPoolTest {
    private static final Integer THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2;
    private static final ExecutorService OD_DISTANCE_THREAD_POOL = new ThreadPoolExecutor(
            1, 1,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder()
                    .namingPattern("od-distance-%s").build());
    private static final int TIMEOUT = 100;

    public static String directReturn(String s) {
        int rondom = new Random().nextInt(10) + 1;
        if (rondom > 5) {
            System.err.println("ArithmeticException");
            throw new ArithmeticException("1/0异常");
        }
        return s;
    }

    public static List<String> query(List<String> taskIds) {
        List<Future<String>> retulst = taskIds.stream().map(taskId -> {
            return OD_DISTANCE_THREAD_POOL.submit(() -> directReturn(taskId));
        }).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        for (Future<String> future : retulst) {
            try {
                list.add(future.get(10, TimeUnit.MILLISECONDS));
            } catch (ArithmeticException e) {
                System.out.println("ArithmeticException test");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException test");
//                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("ExecutionException test");
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("TimeoutException test");
//                e.printStackTrace();
            }
        }
        return list;

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


}
