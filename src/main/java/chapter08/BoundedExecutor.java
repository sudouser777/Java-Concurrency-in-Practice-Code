package chapter08;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

@ThreadSafe
public class BoundedExecutor {

    private final Executor executor;

    private final Semaphore semaphore;

    public BoundedExecutor(Executor executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable task) throws InterruptedException {
        semaphore.acquire();
        try {
            executor.execute(() -> {
                try {
                    task.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException _) {
            semaphore.release();
        }
    }


}
