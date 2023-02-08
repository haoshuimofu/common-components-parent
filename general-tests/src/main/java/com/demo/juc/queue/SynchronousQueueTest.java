package com.demo.juc.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * 同步队列 SynchronousQueue 测试
 * fair=true queue FIFO
 * fair=false stack
 * <p>
 * task时如果queue为空则阻塞
 *
 * @author dewu.de
 * @date 2022-01-26 4:31 下午
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(true);
        int threadNum = 10;
        for (int i = 0; i < threadNum; i++) {
            final int idx = i;
            Thread putThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + " put start");
                    try {
                        queue.put(idx);
                        System.out.println(threadName + " put end");
                    } catch (InterruptedException e) {
                    }
                }
            });
            putThread.setName("put-" + i);
            putThread.start();
        }
        threadNum = 2;
        for (int i = 0; i < threadNum; i++) {
            Thread takeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + " task start");
                    try {
                        Integer i = queue.take();
                        System.out.println(threadName + " task end, value=" + i);
                    } catch (InterruptedException e) {
                    }
                }
            });
            takeThread.setName("task-" + i);
            takeThread.start();
        }
    }

}
