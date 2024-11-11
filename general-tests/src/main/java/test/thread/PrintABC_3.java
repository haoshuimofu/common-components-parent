package test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC_3 {


    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition lock_a = lock.newCondition();
    private static final Condition lock_b = lock.newCondition();
    private static final Condition lock_c = lock.newCondition();


    private static final StringBuilder sb = new StringBuilder();
    private static int last_index = 2;


    public static void main(String[] args) throws Exception {

        Task a = new Task(0);
        Task b = new Task(1);
        Task c = new Task(2);
        c.start();
        b.start();
        a.start();
        while (true) {
            if (a.getState() == Thread.State.TERMINATED
                    && b.getState() == Thread.State.TERMINATED
                    && c.getState() == Thread.State.TERMINATED) {
                System.out.println(sb);
                break;
            }
        }

    }

    static class Task extends Thread {

        private final int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                System.out.println("last_index = " + last_index + ", index = " + index + ", len = " + sb);

                try {
                    int len = sb.length();
                    if (len == 30) {
                        if (index == 0) {
                            lock_b.signal();
                            lock_c.signal();
                        }
                        return;
                    }
                    if (len == 0 || (last_index == 2 && index == 0)) {
                        sb.append("A");
                        last_index = 0;
                        lock_b.signal();
                        lock_a.await();
                    } else if (last_index == 0 && index == 1) {
                        sb.append("B");
                        last_index = 1;
                        lock_c.signal();
                        lock_b.await();
                    } else if (last_index == 1 && index == 2) {
                        sb.append("C");
                        last_index = 2;
                        lock_a.signal();
                        lock_c.await();
                    } else {
                        // 很大概率，会走这个死循环，因为condition && thread并没有绑定
                        System.out.println("!!!last_index = " + last_index + ", index = " + index + ", len = " + sb);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
