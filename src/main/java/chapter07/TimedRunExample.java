package chapter07;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedRunExample {

    private static final ScheduledExecutorService cancelExecutor = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExecutor.schedule(taskThread::interrupt, timeout, unit);
        r.run();
    }

}
