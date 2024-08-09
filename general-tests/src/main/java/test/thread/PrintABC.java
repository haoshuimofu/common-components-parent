package test.thread;

public class PrintABC {

    private static final Object lock = new Object();
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
                synchronized (lock) {
                    if (sb.length() == 30) {
                        return;
                    }
                    if (("A".equals(ch) && (sb.length() == 0 || sb.charAt(sb.length() - 1) == 'C'))
                            || ("B".equals(ch) && sb.charAt(sb.length() - 1) == 'A')
                            || ("C".equals(ch) && sb.charAt(sb.length() - 1) == 'B')) {
                        sb.append(ch);
                        if (sb.length() == 30) {
                            System.out.println(sb.toString());
                        }
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
