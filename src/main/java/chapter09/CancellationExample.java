package chapter09;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CancellationExample {
    final ExecutorService executor = Executors.newCachedThreadPool();
    Future<?> runningTask = null; // Thread confined
    final JButton startButton = new JButton("Start");
    final JButton cancelButton = new JButton("Cancel");

    public void cancel() {


        startButton.addActionListener(e -> {
            if (runningTask == null) {
                runningTask = executor.submit(() -> {
                    while (moreWork()) {
                        if (Thread.currentThread().isInterrupted()) {
                            cleanPartialWork();
                            break;
                        }
                        doSomeWork();
                    }
                });
            }
        });

        cancelButton.addActionListener(e -> {
            if (runningTask != null) {
                runningTask.cancel(true);
            }
        });
    }

    private boolean moreWork() {
        return false;
    }

    private void cleanPartialWork() {

    }

    private void doSomeWork() {

    }
}
