package test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC_2 {


    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition lock_a = lock.newCondition();
    private static final Condition lock_b = lock.newCondition();
    private static final Condition lock_c = lock.newCondition();


    private static final StringBuilder sb = new StringBuilder();
    private static int last_index = -1;


    public static void main(String[] args) throws Exception {

        Task a = new Task(0, lock_a, lock_b);
        Task b = new Task(1, lock_b, lock_c);
        Task c = new Task(2, lock_c, lock_a);
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
        private final Condition curr;
        private final Condition next;

        public Task(int index, Condition curr, Condition next) {
            this.index = index;
            this.curr = curr;
            this.next = next;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                System.out.println("last_index = " + last_index + ", index = " + index + ", len = " + sb);

                try {
                    int len = sb.length();
                    if (len == 30) {
                        if (curr == lock_a) {
                            lock_b.signal();
                            lock_c.signal();
                        }
                        return;
                    }
                    if (len == 0) {
                        if (curr == lock_a) {
                            sb.append("A");
                            next.signal();
                            curr.await();
                        } else {
                            curr.await();
                        }
                    } else {
                        if (curr == lock_a) {
                            sb.append("A");
                            last_index = 0;
                            next.signal();
                            curr.await();
                        } else if (curr == lock_b) {
                            sb.append("B");
                            last_index = 1;
                            next.signal();
                            curr.await();
                        } else if (curr == lock_c) {
                            sb.append("C");
                            last_index = 2;
                            next.signal();
                            curr.await();
                        } else {
                            System.out.println("!!!last_index = " + last_index + ", index = " + index + ", len = " + sb);
                        }
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
