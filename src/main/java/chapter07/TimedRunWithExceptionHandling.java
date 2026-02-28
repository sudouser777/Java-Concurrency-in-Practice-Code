package chapter07;

import chapter05.LaunderThrowable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedRunWithExceptionHandling {

    private static final ScheduledExecutorService cancelExecutor = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r, long timeOut, TimeUnit unit) throws InterruptedException {

        class ReThrowableTask implements Runnable {

            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void reThrow() {
                if (t != null) {
                    throw LaunderThrowable.launderThrowable(t);
                }
            }
        }

        ReThrowableTask task = new ReThrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExecutor.schedule(taskThread::interrupt, timeOut, unit);
        taskThread.join(unit.toMillis(timeOut));
        task.reThrow();
    }
}
