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

    /**
     * ForkJoinPool 和 ThreadPoolExecutor 是两种不同的线程池实现，它们都是 Java 并发框架的一部分，但设计目标和适用场景有所不同。以下是 ForkJoinPool 和 ThreadPoolExecutor 的主要区别：
     * <p>
     * 设计目的与应用场景：
     * <p>
     * ThreadPoolExecutor 是一个通用的线程池实现，适用于处理各类异步任务，尤其是那些相互独立的任务。例如，Web 服务中的请求处理、批处理作业、定时任务等。它旨在高效地管理和调度一组固定数量的线程来执行这些任务，通常这些任务的执行不涉及大量的任务间协作或依赖关系。
     * ForkJoinPool 是专门为支持“分治算法”（divide-and-conquer algorithms）而设计的线程池，特别适合处理可以递归分解成子任务的计算密集型任务，且子任务之间可能存在大量的同步点和结果合并操作。例如，数组排序、图算法、大数据集的遍历与计算等。ForkJoinPool 最初是在 Java 7 中引入的，作为 java.util.concurrent.ForkJoinTask 类及其子类（如 RecursiveAction 和 RecursiveTask）的执行环境。
     * 工作窃取（Work Stealing）策略：
     * <p>
     * ThreadPoolExecutor 通常采用阻塞队列来存储待处理任务，并且当工作线程空闲时，它们会从队列中取出任务来执行。如果队列为空，线程可能会进入等待状态或者被终止，具体取决于线程池的配置。这种策略对于处理独立任务较为有效，但对于存在大量互相依赖子任务的场景，可能会导致线程间的负载不平衡。
     * ForkJoinPool 实现了一种先进的工作窃取（Work Stealing）算法。每个工作线程都有自己的双端队列来存储子任务。当一个线程完成当前任务且本地队列为空时，它会尝试从其他线程的队列末尾“偷取”任务来执行，而不是直接进入等待状态。这种策略有助于保持所有工作线程的活跃度，尤其在处理大量可分解任务时，能更有效地利用多核处理器的计算能力，减少线程上下文切换开销，并保持良好的负载均衡。
     * 任务提交与执行模型：
     * <p>
     * ThreadPoolExecutor 接受 Runnable 或 Callable 对象作为任务提交给线程池。任务一旦提交，线程池将负责调度合适的线程来执行。任务之间的同步和通信通常需要显式使用 Future、CountDownLatch、CyclicBarrier 等并发工具进行协调。
     * ForkJoinPool 使用特殊的 ForkJoinTask 对象，它提供了 fork() 和 join() 方法来支持任务的递归分解与结果合并。fork() 用于非阻塞地提交一个子任务，并立即返回，使得当前任务可以继续分解更多的子任务或执行其他操作。join() 用于获取子任务的结果，它会阻塞直到子任务完成。这种模型简化了任务间的协作和结果聚合，使得并行计算更为直观。
     * 异常处理：
     * <p>
     * ThreadPoolExecutor 中，如果一个任务抛出未捕获的异常，该异常会被封装在 Future.get() 调用时抛出的 ExecutionException 中，或者触发 UncaughtExceptionHandler。异常通常会导致任务终止，但不会直接影响其他任务的执行。
     * ForkJoinPool 中，如果一个任务抛出异常，该异常会被传播到调用 join() 的地方。如果异常没有被捕获，它将导致整个计算过程失败，并可能影响其他相关联的任务。
     * 优先级与亲和性：
     * <p>
     * ThreadPoolExecutor 提供了更多配置选项，如设置线程优先级、指定线程工厂、使用优先级队列等，可以根据应用需求调整任务的执行顺序或线程属性。
     * ForkJoinPool 为了优化工作窃取效率，默认情况下会对工作线程进行亲和性绑定（即每个工作线程通常只在特定 CPU 核心上运行），并且不直接支持任务优先级。其任务调度主要依赖于工作窃取策略来实现动态平衡。
     * 总结起来，ThreadPoolExecutor 是一个通用且灵活的线程池实现，适用于处理各种独立的异步任务，提供了丰富的配置选项以适应不同应用场景。而 ForkJoinPool 是针对特定类型并行计算设计的线程池，专注于处理可递归分解的计算密集型任务，通过工作窃取算法实现高效的并行计算和结果合并。选择使用哪种线程池应根据具体的任务特性和性能需求来决定。
     *
     * @param args
     */
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
