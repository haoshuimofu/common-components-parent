package test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC_Lock_Condition {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition A = lock.newCondition();
    private static Condition B = lock.newCondition();
    private static Condition C = lock.newCondition();
    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        PrintABC_Lock.Task a = new PrintABC_Lock.Task("A");
        PrintABC_Lock.Task b = new PrintABC_Lock.Task("B");
        PrintABC_Lock.Task c = new PrintABC_Lock.Task("C");
        a.start();
        b.start();
        c.start();
    }



    static class Task extends Thread {

        private final String ch;

        Task(String ch) {
            this.ch = ch;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (sb.length() == 30) {
                        return;
                    }
                    if ("A".equals(ch) && (sb.length() == 0 || sb.charAt(sb.length() - 1) == 'C')){
                        sb.append(ch);
                        A.await();
                        B.signal();
                    } else if ("B".equals(ch)  && sb.charAt(sb.length() - 1) == 'A'){
                        sb.append(ch);
                        B.await();
                        C.signal();
                    } else if ("C".equals(ch) && sb.charAt(sb.length() - 1) == 'B') {
                        sb.append(ch);
                        C.await();
                        A.signal();
                    }
                    if (sb.length() == 30) {
                        System.out.println(sb.toString());
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
