package test.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author wude
 * @date 2021/6/5 16:15
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(i, countDownLatch)).start();
        }
        countDownLatch.await();
        System.out.println("线程执行结束。。。。");
    }

    static class Task implements Runnable {
        private int num;
        private CountDownLatch latch;

        public Task(int num, CountDownLatch latch) {
            this.num = num;
            this.latch = latch;
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println("num = " + num + "; thread = " + Thread.currentThread().getName());
                latch.countDown();
            }
        }
    }


}