package chapter12;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {
    static final ExecutorService executor = Executors.newCachedThreadPool();

    final AtomicInteger putSum = new AtomicInteger();

    final AtomicInteger takeSum = new AtomicInteger();

    final CyclicBarrier barrier;

    final BoundedBuffer<Integer> buffer;

    final int nPairs, nTrials;


    public PutTakeTest(int capacity, int nPairs, int nTrials) {
        this(capacity, nPairs, nTrials, new CyclicBarrier(nPairs * 2 + 1));
    }

    public PutTakeTest(int capacity, int nPairs, int nTrials, CyclicBarrier barrier) {
        this.buffer = new BoundedBuffer<>(capacity);
        this.nPairs = nPairs;
        this.nTrials = nTrials;
        this.barrier = barrier;
    }

    static void main() throws InterruptedException {
        new PutTakeTest(10, 10, 100000).test();
        executor.shutdown();
    }


    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                executor.execute(new Producer());
                executor.execute(new Consumer());
            }
            barrier.await();
            barrier.await();
            System.out.println("putSum: " + putSum);
            System.out.println("takeSum: " + takeSum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    int xorShift(int x) {
        x ^= (x << 6);
        x ^= (x >>> 21);
        x ^= (x << 7);
        return x;
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int seed = (int) (this.hashCode() ^ System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = 0; i < nTrials; i++) {
                    buffer.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = 0; i < nTrials; i++) {
                    sum += buffer.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
