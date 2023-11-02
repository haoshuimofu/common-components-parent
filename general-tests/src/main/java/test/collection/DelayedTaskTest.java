package test.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author dewu.de
 * @date 2023-11-02 2:11 下午
 */
public class DelayedTaskTest {

    private static final DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();


    @AllArgsConstructor
    @Getter
    static class DelayedTask implements Delayed {

        private final int index;
        private final long submitMillis;
        private final long delayMillis;

        @Override
        public long getDelay(@NotNull TimeUnit unit) {
            return unit.convert(this.delayMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(@NotNull Delayed other) {
            return Long.compare(this.delayMillis, ((DelayedTask) other).getDelayMillis());
        }

        public void execute() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.Z");
            System.out.println(index + " submit at " + sdf.format(new Date(submitMillis)) + ", and execute at " + sdf.format(new Date(delayMillis)));
        }

    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            try {
                while (true) {
                    DelayedTask task = delayQueue.take();
                    task.execute();
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        });

        for (int i = 0; i < 10; i++) {
            DelayedTask task = new DelayedTask(i, System.currentTimeMillis(), System.currentTimeMillis() + (10 - i) * 5 * 1000);
            delayQueue.offer(task);
        }
    }
}
