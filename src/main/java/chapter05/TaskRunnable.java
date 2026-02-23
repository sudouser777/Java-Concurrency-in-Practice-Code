package chapter05;

import java.util.concurrent.BlockingQueue;

public class TaskRunnable implements Runnable {

    BlockingQueue<Task> taskQueue;

    @Override
    public void run() {
        try {
            processTask(taskQueue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    private void processTask(Task task) {

    }
}
