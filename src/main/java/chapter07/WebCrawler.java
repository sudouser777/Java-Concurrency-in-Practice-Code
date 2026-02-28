package chapter07;

import net.jcip.annotations.GuardedBy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class WebCrawler {

    private static final long TIMEOUT = 10;
    private volatile TrackingExecutor executor;

    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<>();

    public synchronized void start() {
        executor = new TrackingExecutor(Executors.newCachedThreadPool());
        urlsToCrawl.forEach(this::submitCrawlTask);
        urlsToCrawl.clear();
    }

    public synchronized void stop() throws InterruptedException {
        try {
            savedUncrawled(executor.shutdownNow());
            if (executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                savedUncrawled(executor.shutdownNow());
            }
        } finally {
            executor = null;
        }
    }

    private void savedUncrawled(List<Runnable> unCrawled) {
        unCrawled.forEach(task -> urlsToCrawl.add(((CrawlTask) task).getPage()));
    }

    private void submitCrawlTask(URL url) {
        executor.execute(new CrawlTask(url));
    }


    protected abstract List<URL> processPage(URL url);

    public class CrawlTask implements Runnable {

        private final URL page;

        public CrawlTask(URL page) {
            this.page = page;
        }

        public URL getPage() {
            return page;
        }

        @Override
        public void run() {
            for (URL url : processPage(page)) {
                if (Thread.currentThread().isInterrupted())
                    return;
                submitCrawlTask(url);
            }
        }
    }
}
