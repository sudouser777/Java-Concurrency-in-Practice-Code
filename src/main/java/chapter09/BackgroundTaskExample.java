package chapter09;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

public class BackgroundTaskExample {

    ExecutorService executor = Executors.newCachedThreadPool();

    final JButton startButton = new JButton("Start");

    final JButton cancelButton = new JButton("Cancel");

    final JLabel label = new JLabel();

    public void runInBackground(final Runnable task) {
        startButton.addActionListener(e -> {
            class CancelListener implements ActionListener{
                BackgroundTask<?> task;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(task != null) {
                        task.cancel(true);
                    }
                }
            }

            final CancelListener listener = new CancelListener();
            listener.task = new BackgroundTask<Object>() {
                @Override
                protected Object compute() throws Exception {
                    while (moreWork() && !isCancelled()){
                        doSomeWork();
                    }
                    return null;
                }

                @Override
                protected void onCompletion(Object result, Throwable exception, boolean cancelled) {
                    cancelButton.removeActionListener(listener);
                    label.setText("done");
                }
            };
            cancelButton.addActionListener(listener);
            executor.execute(listener.task);
        });

    }

    private void doSomeWork() {

    }

    private boolean moreWork() {
        return false;
    }
}
