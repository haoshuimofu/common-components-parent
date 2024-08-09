package test.thread;

public class SyncTest {

    private static int sum = 0;

    /**
     * synchronized 锁的是类对象
     */
    public synchronized static void add() {
        sum +=1;
    }

    /**
     * synchronized 锁的是类对象
     */
    public synchronized static void add1() {
        synchronized (SyncTest.class) {
            sum +=1;
        }
    }

    /**
     * 锁的是当前实例对象
     */
    public void add2() {
        synchronized (this) {
            sum +=1;
        }
    }

    private Object lock = new Object();
    /**
     * 锁的是当前实例对象
     */
    public void add3() {
        synchronized (lock) {
            sum +=1;
        }
    }
}
