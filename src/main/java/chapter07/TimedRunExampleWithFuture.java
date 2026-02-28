package chapter07;

import chapter05.LaunderThrowable;

import java.util.concurrent.*;

public class TimedRunExampleWithFuture {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> future = executor.submit(r);

        try {
            future.get(timeout, unit);
        } catch (TimeoutException e) {
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e);
        } finally {
            future.cancel(true);
        }
    }

}
