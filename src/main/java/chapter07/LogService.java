package chapter07;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogService {

    private final BlockingQueue<String> queue;

    private final LoggerThread loggerThread;

    private final PrintWriter writer;

    @GuardedBy("this")
    private boolean isShutdown;

    @GuardedBy("this")
    private int reservations;

    public LogService(Writer writer) {
        this.writer = new PrintWriter(writer);
        this.queue = new LinkedBlockingQueue<>();
        this.loggerThread = new LoggerThread();
    }

    public void start() {
        loggerThread.start();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException("logger is shut down");
            }
            ++reservations;
        }
        queue.put(msg);
    }


    public void stop() {
        synchronized (this) {
            isShutdown = true;
            loggerThread.interrupt();
        }
    }


    private class LoggerThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    try{
                        synchronized (LogService.this) {
                            if(isShutdown && reservations == 0)
                                break;
                            String msg = queue.take();
                            synchronized (LogService.this) {
                                --reservations;
                            }
                            writer.println(msg);
                        }
                    } catch (InterruptedException _) {
                        // retry
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
