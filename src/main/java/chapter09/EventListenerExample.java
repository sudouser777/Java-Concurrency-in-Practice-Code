package chapter09;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventListenerExample {

    ExecutorService executor = Executors.newCachedThreadPool();

    public void addColorChangeListener() {
        final Random random = new Random();
        final JButton button = new JButton("Change color");

        button.addActionListener(e -> button.setBackground(new Color(random.nextInt())));
    }

    public void addBackgroundTask() {
        final JButton button = new JButton("Do background task");
        button.addActionListener(e -> executor.submit(this::doBigComputation));
    }

    private void doBigComputation() {

    }

    public void addActionListenerWithFeedeback() {
        final JButton button = new JButton("idle");
        button.addActionListener(e -> {
            button.setEnabled(false);
            button.setText("busy");
            executor.execute(() -> {
                try{
                    doBigComputation();
                }finally {
                    GuiExecutor.getInstance().execute(() -> {
                        button.setEnabled(true);
                        button.setText("idle");
                    });
                }
            });
        });
    }

}
