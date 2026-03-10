package chapter12;

import org.junit.jupiter.api.Test;

public class ResourceLeakTest {

    private static final int CAPACITY = 100;
    class Big{
        double[] data = new double[100_000];
    }

    @Test
    void testLeak() throws InterruptedException {
        BoundedBuffer<Big> b = new BoundedBuffer<>(CAPACITY);
        long heapSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        for (int i = 0; i < CAPACITY; i++) {
            b.put(new Big());
        }

        for(int i = 0; i < CAPACITY; i++) {
            b.take();
        }

        long heapSizeAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Heap changed by: " + (heapSizeAfter - heapSize) + " bytes");
    }

}
