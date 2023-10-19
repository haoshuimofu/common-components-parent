package test.thread;

/**
 * @author dewu.de
 * @date 2023-10-19 2:29 下午
 */
public class MyThread extends Thread {

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Perform some long-running task


            // Check if the thread has been interrupted
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread interrupted. Stopping...");
                break;
            }
        }
    }


    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();

        // Interrupt the thread after 3 seconds
        try {
            Thread.sleep(3000);
            thread.interrupt();
        } catch (InterruptedException e) {
            // 什么时候会抛这个异常?
            e.printStackTrace();
        }
    }

}
