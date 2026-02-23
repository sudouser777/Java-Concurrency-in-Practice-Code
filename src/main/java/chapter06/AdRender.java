package chapter06;

import java.util.concurrent.*;

public class AdRender {

        private static final long TIME_BUDGET = 1_000_000_000;
        private static final Ad DEFAULT_AD = new Ad();

        private final ExecutorService executorService = Executors.newFixedThreadPool(10);

        public Page renderPageWithAd() throws InterruptedException {
            long endNanos = System.nanoTime() + TIME_BUDGET;
            Future<Ad> futureAd = executorService.submit(new FetchAdTask<>());
            Page page = renderPageBody();
            Ad ad;
            long timeLeft = endNanos - System.nanoTime();
            try {
                ad = futureAd.get(timeLeft, TimeUnit.NANOSECONDS);
            }  catch (ExecutionException e) {
                ad = DEFAULT_AD;
            } catch (TimeoutException e) {
                ad = DEFAULT_AD;
                futureAd.cancel(true);
            }
            page.setAd(ad);
            return page;
        }

    private Page renderPageBody() {
        return new Page();
    }


    public static class Ad {}

        private class FetchAdTask<V> implements Callable<V> {

            @Override
            public V call() throws Exception {
                return null;
            }
        }
}
