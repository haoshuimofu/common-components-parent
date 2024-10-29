package gc;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GCTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            generateHumongousObject();
//            oom();
        }, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    public static void generateHumongousObject() {
        byte[] bytes = new byte[1024 * 1024 * 10];
    }

    /**
     * java.lang.OutOfMemoryError: Java heap space
     * Dumping heap to D:\data\logs\java_pid11644.hprof ...
     * Heap dump file created [3577694980 bytes in 20.162 secs]
     *
     * Process finished with exit code 130
     */
    public static void oom() {
        byte[][] bytes = new byte[1024][1024 * 1024 * 10];
        System.out.println(bytes.length);
        for (int i = 0; i < bytes.length; i++) {

        }
    }

}
