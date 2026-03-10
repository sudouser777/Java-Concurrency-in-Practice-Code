package chapter12;

import org.junit.jupiter.api.Assertions;

import java.util.concurrent.CyclicBarrier;

public class TimedPutTakeTest extends PutTakeTest {

    private final BarrierTimer timer;

    public TimedPutTakeTest(int capacity, int nPairs, int nTrials) {
        BarrierTimer timer = new BarrierTimer();
        super(capacity, nPairs, nTrials, new CyclicBarrier(nPairs * 2 + 1, timer));
        this.timer = timer;
    }

    void test() {
        try {
            timer.clear();
            for (int i = 0; i < nPairs; i++) {
                executor.execute(new Producer());
                executor.execute(new Consumer());
            }
            barrier.await();
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
            System.out.println("Throughput: " + nsPerItem + " ns/item");
            Assertions.assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void main() throws InterruptedException {
        int tpt = 100_000;
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            TimedPutTakeTest timedPutTakeTest = new TimedPutTakeTest(cap, 4, tpt);
            timedPutTakeTest.test();
            System.out.println("\t");
            Thread.sleep(1000);
            timedPutTakeTest.test();
            System.out.println();
            Thread.sleep(1000);
        }
        executor.shutdown();
    }
}
