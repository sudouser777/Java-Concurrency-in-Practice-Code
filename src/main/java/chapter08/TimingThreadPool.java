package chapter08;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class TimingThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final Logger log = Logger.getLogger(TimingThreadPool.class.getName());

    private final AtomicLong numTasks = new AtomicLong();

    private final AtomicLong totalTime = new AtomicLong();


    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.fine("Thread %s: start %s".formatted(t, r));
        startTime.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.currentTimeMillis();
            long taskTime = endTime - startTime.get();
            totalTime.addAndGet(taskTime);
            numTasks.incrementAndGet();
            log.fine("Thread %s: end %s, taskTime %d".formatted(t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }

    }

    @Override
    protected void terminated() {
        try {
            log.info("Terminated: avg time=%dns".formatted(totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }
}
