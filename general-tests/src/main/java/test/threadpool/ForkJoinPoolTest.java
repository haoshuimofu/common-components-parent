package test.threadpool;

import test.threadpool.model.ForkJoinAction;
import test.threadpool.model.ForkJoinCallable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

/**
 * @author dewu.de
 * @date 2023-02-22 11:00 上午
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) {

        int batch = 10;
        List<ForkJoinAction> tasks = new ArrayList<>();
        for (int i = 0; i < batch; i++) {
            int finalI = i;
            ForkJoinAction task = new ForkJoinAction(() -> {
                printSomething(finalI);
                return null;
            });
            task.fork();
            tasks.add(task);
        }
        tasks.forEach(ForkJoinTask::join);

        System.out.println();
        System.out.println(">>> --- <<<");
        System.out.println();

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null, false);

        ForkJoinPool forkJoinPool1 = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null, false);

        List<ForkJoinCallable> callables = new ArrayList<>();
        for (int i = 0; i < batch; i++) {
            int finalI = i;
            ForkJoinCallable callable = new ForkJoinCallable(() -> {
                printSomething(finalI);
                return null;
            });
            callables.add(callable);
        }
        List<Future<Void>> futures = forkJoinPool1.invokeAll(callables);
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private static void printSomething(int i) {
        System.out.println(i + " ---> " + Thread.currentThread().getName());
    }

}
