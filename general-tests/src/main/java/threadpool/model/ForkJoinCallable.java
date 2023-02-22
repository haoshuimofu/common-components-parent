package threadpool.model;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author dewu.de
 * @date 2023-02-22 11:05 上午
 */
public class ForkJoinCallable implements Callable<Void> {

    private final Supplier<Void> supplier;

    public ForkJoinCallable(Supplier<Void> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Void call() throws Exception {
        supplier.get();
        return null;
    }
}
