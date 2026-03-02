package chapter08;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyAppThread extends Thread {

    public static final String DEFAULT_NAME = "MyAppThread";

    private static volatile boolean debugLifeCycle = false;

    private static final AtomicInteger created = new AtomicInteger();

    private static final AtomicInteger alive = new AtomicInteger();

    private static final Logger log = Logger.getAnonymousLogger();


    public MyAppThread(Runnable task) {
        this(task, DEFAULT_NAME);
    }

    public MyAppThread(Runnable r, String name) {
        super(r, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.log(Level.SEVERE, "Uncaught exception in thread " + t.getName(), e);
                    }
                }
        );

    }

    @Override
    public void run() {
        boolean debug = debugLifeCycle;
        if (debug) {
            log.log(Level.FINE, "Created " + getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) {
                log.log(Level.FINE, "Exiting " + getName());
            }
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static boolean getDebug() {
        return debugLifeCycle;
    }

    public static void setDebug(boolean debug) {
        debugLifeCycle = debug;
    }
}
