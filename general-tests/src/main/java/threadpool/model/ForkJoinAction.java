package threadpool.model;

import java.util.concurrent.RecursiveAction;
import java.util.function.Supplier;

/**
 * @author dewu.de
 * @date 2023-02-22 11:07 上午
 */
public class ForkJoinAction extends RecursiveAction {

    private final Supplier<Void> supplier;

    public ForkJoinAction(Supplier<Void> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected void compute() {
        supplier.get();
    }
}
