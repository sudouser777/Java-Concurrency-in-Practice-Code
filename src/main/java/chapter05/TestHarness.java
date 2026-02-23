package chapter05;

import java.util.concurrent.CountDownLatch;

public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            new Thread(() -> {
                try {
                    startGate.await();
                    task.run();
                    endGate.countDown();
                } catch (InterruptedException ignored) {

                }
            }).start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        return System.nanoTime() - start;
    }

    static void main() {
        TestHarness harness = new TestHarness();
        Runnable task = () -> System.out.println("Executing task by thread: " + Thread.currentThread().getName());
        try {
            long time = harness.timeTasks(5, task);
            System.out.println("Time taken: " + time + " nanoseconds");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
