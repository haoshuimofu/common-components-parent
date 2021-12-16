package com.demo.jdk8;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dewu.de
 * @date 2021-12-16 2:36 下午
 */
public class FutureDemo {

    public static void main(String[] args) throws Exception {
        // 1. 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 2. 提交任务
        List<Callable<String>> tasks = new ArrayList<>();
        int taskSize = 100;
        ArrayList<Integer> taskIds = new ArrayList<>(taskSize);
        for (int i = 0; i < taskSize; i++) {
            taskIds.add(i + 1);
        }
        taskIds.forEach(taskId -> {
            Callable<String> task = () -> {
                Thread.sleep(1000);
                String format = "yyyy-MM-dd HH:mm:ss.SSS";
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
//                SimpleDateFormat sdf = new SimpleDateFormat(format);
//                time = sdf.format(new Date());
                return "任务" + taskId + "执行完成！" + time;
            };
            tasks.add(task);
        });
        List<Future<String>> futures = executorService.invokeAll(tasks);
        // 3. 获取任务执行结果
        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
        }
        executorService.shutdown();
    }

}
