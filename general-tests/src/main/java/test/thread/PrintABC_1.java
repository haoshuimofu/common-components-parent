package test.thread;

public class PrintABC_1 {

    private static final StringBuilder sb = new StringBuilder();
    private static final Object lock = new Object();

    public static void main(String[] args) {

        Task a = new Task(0);
        Task b = new Task(1);
        Task c = new Task(2);
        a.start();
        b.start();
        c.start();
        System.out.println(a.isAlive());
        System.out.println(a.isInterrupted());
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
                synchronized (lock) {
                    int len = sb.length();
                    if (len == 30) {
                        System.out.println("长度已达30, 线程终止, thread=" + Thread.currentThread().getName());
                        return;
                    }
                    int mod = len % 3;
                    if (mod == 0 && index == 0) {
                        sb.append("A");
                        lock.notifyAll();
                    } else if (mod == 1 && index == 1) {
                        sb.append("B");
                        lock.notifyAll();
                    } else if (mod == 2 && index == 2) {
                        sb.append("C");
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
