package chapter01;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ThreadSafe
public class Sequence {

    @GuardedBy("this")
    private int value;

    public synchronized int getNext() {
        return value++;
    }

    static class Driver {
        public static void main(String[] args) {
            int nThreads = 7;
            int operationsPerThread = 1111;

            Sequence sequence = new Sequence();
            try(ExecutorService executorService = Executors.newCachedThreadPool()) {
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
