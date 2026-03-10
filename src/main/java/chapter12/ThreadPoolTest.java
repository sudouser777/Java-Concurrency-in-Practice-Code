package chapter12;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadPoolTest {

    private final TestingThreadFactory threadFactory = new TestingThreadFactory();

    @Test
    public void testPoolExpansion() throws InterruptedException {
        int maxSize = 10;
        ExecutorService executor = Executors.newFixedThreadPool(maxSize, threadFactory);

        for (int i = 0; i < maxSize; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 20 && threadFactory.numCreated.get() < maxSize; i++) {
            Thread.sleep(100);
        }

        assertEquals(maxSize, threadFactory.numCreated.get());
        executor.shutdown();
    }
}
