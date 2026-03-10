package chapter12;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Semaphore;

@ThreadSafe
public class BoundedBuffer<E> {

    private final Semaphore availableItems, availableSpaces;

    @GuardedBy("this")
    private final E[] elements;

    @GuardedBy("this")
    private int putPosition = 0, takePosition = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        elements = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E e) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(e);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E e = doExtract();
        availableSpaces.release();
        return e;
    }

    private synchronized void doInsert(E e) {
        int i = putPosition;
        elements[i] = e;
        putPosition = (putPosition + 1) % elements.length;
    }

    private synchronized E doExtract() throws InterruptedException {
        int i = takePosition;
        E e = elements[i];
        elements[i] = null;
        takePosition = (takePosition + 1) % elements.length;
        return e;
    }


}
