package test.threadpool;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class JVMThread {

    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // 获取线程总数
        int totalThreadCount = threadMXBean.getThreadCount();

        // 获取死锁线程数
        long deadlockCount = threadMXBean.findDeadlockedThreads() == null ? 0 : threadMXBean.findDeadlockedThreads().length;

        // 获取新建线程数
        // 需要先获取初始线程数
        int initialThreadCount = threadMXBean.getThreadCount();
        // 假设这里做一些操作来创建新线程
        // ...
        // 再次获取线程数减去初始数量就是新建线程数
        int newThreadCount = threadMXBean.getThreadCount() - initialThreadCount;

        // 获取阻塞线程数
//        int blockedThreadCount = threadMXBean.getBlockedCount();

        // 获取可运行线程数
//        int runnableThreadCount = threadMXBean.getTotalStartedThreadCount();

        System.out.println("CPU核数: " + Runtime.getRuntime().availableProcessors());
        System.out.println("总线程数: " + totalThreadCount);
        System.out.println("死锁线程数: " + deadlockCount);
        System.out.println("新建线程数: " + newThreadCount);
//        System.out.println("阻塞线程数: " + blockedThreadCount);
//        System.out.println("可运行线程数: " + runnableThreadCount);
    }

}
