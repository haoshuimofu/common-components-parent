package test.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wude
 * @date 2021/6/5 16:16
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(i, cyclicBarrier)).start();
        }
        cyclicBarrier.await();
        System.out.println("线程组执行完毕1......");
        // CyclicBarrier 可以重复利用，
        // 这个是CountDownLatch做不到的
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(i, cyclicBarrier)).start();
        }
        cyclicBarrier.await();
        System.out.println("线程组执行完毕2......");
    }

    static class Task implements Runnable {

        private int num;
        private CyclicBarrier cyc;

        public Task(int num, CyclicBarrier cyc) {
            this.num = num;
            this.cyc = cyc;
        }

        @Override
        public void run() {
            System.out.println("num = " + num + "; thread = " + Thread.currentThread().getName());
            try {
                cyc.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }

}