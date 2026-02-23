package chapter05;

import java.io.File;
import java.util.concurrent.BlockingDeque;

public class Indexer implements Runnable {

    private final BlockingDeque<File> queue;

    public Indexer(BlockingDeque<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File file) {
        System.out.println("Thread: " + Thread.currentThread().getName() + "Indexing file: " + file.getAbsolutePath());
    }
}
