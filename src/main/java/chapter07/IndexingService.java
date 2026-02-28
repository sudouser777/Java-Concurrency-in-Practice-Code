package chapter07;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

public class IndexingService {

    private static final File POISION = new File("");

    private final IndexerThread consumer = new IndexerThread();

    private final CrawlerThread producer = new CrawlerThread();

    private final BlockingQueue<File> queue;

    private final FileFilter filter;

    private final File root;

    public IndexingService(BlockingQueue<File> queue, FileFilter filter, File root) {
        this.queue = queue;
        this.filter = filter;
        this.root = root;
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    class IndexerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException _) {
            } finally {
                while (true) {
                    try {
                        queue.put(POISION);
                        break;
                    } catch (InterruptedException _) {
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {

        }
    }


    class CrawlerThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISION)
                        break;
                    else
                        indexFile(file);
                }
            } catch (InterruptedException _) {

            }
        }

        private void indexFile(File file) {

        }
    }
}
