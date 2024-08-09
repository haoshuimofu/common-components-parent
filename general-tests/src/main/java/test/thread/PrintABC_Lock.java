package test.thread;

import java.util.concurrent.locks.ReentrantLock;

public class PrintABC_Lock {

    private static ReentrantLock lock = new ReentrantLock();
    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Task a = new Task("A");
        Task b = new Task("B");
        Task c = new Task("C");
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
                    if (("A".equals(ch) && (sb.length() == 0 || sb.charAt(sb.length() - 1) == 'C'))
                            || ("B".equals(ch) && sb.length() > 0 && sb.charAt(sb.length() - 1) == 'A')
                            || ("C".equals(ch) && sb.length() > 0 && sb.charAt(sb.length() - 1) == 'B')) {
                        sb.append(ch);
                        if (sb.length() == 30) {
                            System.out.println(sb.toString());
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }

    }

}
