package test.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-12-13 13:00
 */
public class ExecutorsTest {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        // 在设定延迟时间后启动Task
        // executorService.schedule(new Task(), 5, TimeUnit.SECONDS);

        // 初始化延迟10S后启动, 以后每5S启动一次, 这个时候就不能shutdown停止线程池接收新任务了, 而且如果task本身执行时长超过period, 那么本次task执行完毕以后下次task立即执行
        // --------> 延迟时间计算始于上个task的《《启动》》时间，而如果task执行时间大于间隔时间，那么延迟失效
        executorService.scheduleAtFixedRate(new Task(), 10, 5, TimeUnit.SECONDS);

        // 初始化延迟10S后启动, 以后每5S启动一次, 这个时候就不能shutdown停止线程池接收新任务了, 而且如果task本身执行时长超过period, 那么本次task执行完毕以后还是会等待延迟时间后下次task才执行
        // --------> 延迟时间计算始于上个task的《《结束》》时间，而如果task执行时间大于间隔时间无影响
//        executorService.scheduleWithFixedDelay(new Task(), 10, 5, TimeUnit.SECONDS);

//        executorService.shutdown();
//        try {
//            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        executorService.shutdown();


    }


    static class Task implements Runnable {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("now is " + sdf.format(new Date()));
        }

    }
}
