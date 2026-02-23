package chapter01;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@NotThreadSafe
public class UnsafeSequence {

    private int value;

    public int getNext() {
        return value++;
    }

    static class Driver {
        static void main(String[] args) {
            int nThreads = 7;
            int operationsPerThread = 1111;

            UnsafeSequence sequence = new UnsafeSequence();
            try (ExecutorService executorService = Executors.newCachedThreadPool()) {
                for (int i = 0; i < nThreads; i++) {
                    executorService.submit(() -> {
                        for (int j = 0; j < operationsPerThread; j++) {
                            sequence.getNext();
                        }
                    });
                }
                executorService.shutdown();
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            System.out.println("Expected: " + (nThreads * operationsPerThread));
            System.out.println("Actual: " + sequence.getNext());
        }
    }
}
