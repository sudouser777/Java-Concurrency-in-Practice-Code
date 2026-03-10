package chapter12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBufferTest {

    @Test
    void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> b = new BoundedBuffer<>(10);
        assertTrue(b.isEmpty());
        assertFalse(b.isFull());
    }

    @Test
    void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> b = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            b.put(i);
        }
        assertTrue(b.isFull());
        assertFalse(b.isEmpty());
    }

    @Test
    void testTakeBlocksWhenEmpty() throws InterruptedException {
        final BoundedBuffer<Integer> b = new BoundedBuffer<>(10);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    int ele = b.take();
                    fail(); // Should not get here
                } catch (InterruptedException _) {
                }
            }
        };

        try {
            t.start();
            Thread.sleep(1000);
            t.interrupt();
            t.join(1000);
        } catch (InterruptedException e) {
            fail();
        }
    }
}