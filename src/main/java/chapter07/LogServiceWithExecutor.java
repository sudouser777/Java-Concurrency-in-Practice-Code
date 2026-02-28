package chapter07;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class LogServiceWithExecutor {

    private final PrintWriter writer;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LogServiceWithExecutor(Writer writer) {
        this.writer = new PrintWriter(writer);
    }


    public void log(String msg) throws InterruptedException {
        try {
            executor.execute(new WriteTask(msg));
        } catch (RejectedExecutionException _) {
        }
    }

    public void start() {
    }

    public void stop() throws InterruptedException {
        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }

    class WriteTask implements Runnable {

        private final String msg;

        public WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.write(msg);
        }
    }

}
