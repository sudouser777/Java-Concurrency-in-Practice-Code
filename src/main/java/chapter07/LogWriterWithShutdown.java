package chapter07;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriterWithShutdown {

    private final BlockingQueue<String> queue;

    private final LoggerThread logger;

    private volatile boolean shutdownRequested;

    public LogWriterWithShutdown(Writer writer) {
        this.queue = new LinkedBlockingQueue<>();
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        if (!shutdownRequested)
            queue.put(msg);
        else
            throw new IllegalStateException("logger is shut down");
    }


    public void shutdown() {
        shutdownRequested = true;
    }


    private class LoggerThread extends Thread {
        private final PrintWriter writer;


        private LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (InterruptedException _) {

            } finally {
                writer.close();
            }
        }
    }
}
