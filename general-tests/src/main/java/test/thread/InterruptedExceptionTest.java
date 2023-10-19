package test.thread;

/**
 * @author dewu.de
 * @date 2023-10-19 3:13 下午
 */
public class InterruptedExceptionTest extends Thread {

    private Object lock = new Object();

    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("run...");
            try {
                Thread.sleep(30_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //调用了Thread.sleep()方法时，在线程睡眠期间，如果其他线程调用了该线程的interrupt()方法，就会抛出InterruptedException异常。
        MyThread t = new MyThread();
        t.start();
        t.interrupt();
    }

    public void run111() {


        //InterruptedException是一个被检查异常，它通常在涉及线程的阻塞操作时被抛出。以下是一些会导致InterruptedException被抛出的常见情况：

        //调用了Thread.sleep()方法时，在线程睡眠期间，如果其他线程调用了该线程的interrupt()方法，就会抛出InterruptedException异常。
        try {
            Thread.sleep(5000); // 线程睡眠5秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //调用了Object.wait()方法时，在等待期间，如果其他线程调用了该线程的interrupt()方法，就会抛出InterruptedException异常。
        synchronized (lock) {
            try {
                lock.wait(); // 线程进入等待状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 调用了Thread.join()方法时，在等待被加入的线程执行完成期间，如果主线程被interrupted，就会抛出InterruptedException异常。
//        try {
//            thread.join(); // 等待thread线程执行完成
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 需要注意的是，当InterruptedException被抛出时，线程的中断状态会被清除，

        // 即isInterrupted()方法会返回false。因此，在捕获InterruptedException异常后，我们通常会重新设置线程的中断状态，以便在需要的时候终止线程的执行。

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // 重新设置中断状态
        }
    }

}
