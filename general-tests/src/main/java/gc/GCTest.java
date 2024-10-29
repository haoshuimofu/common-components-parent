package gc;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GCTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            generateHumongousObject();
//            oom();
            headMixedGc();
        }, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    public static void generateHumongousObject() {
        byte[] bytes = new byte[1024 * 1024 * 10];
    }

    /**
     * java.lang.OutOfMemoryError: Java heap space
     * Dumping heap to D:\data\logs\java_pid11644.hprof ...
     * Heap dump file created [3577694980 bytes in 20.162 secs]
     * <p>
     * Process finished with exit code 130
     */
    public static void oom() {
        byte[][] bytes = new byte[1024][1024 * 1024 * 10];
        System.out.println(bytes.length);
        for (int i = 0; i < bytes.length; i++) {

        }
    }


    public static void headMixedGc() {
        List<byte[]> byteList = new LinkedList<>();
        while (true) {
            byteList.add(new byte[1024 * 1024]);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // throw new RuntimeException(e);
            }
        }
    }

}
