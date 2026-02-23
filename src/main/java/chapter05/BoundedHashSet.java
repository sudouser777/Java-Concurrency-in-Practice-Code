package chapter05;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<E> {

    private final Set<E> set;

    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.semaphore = new Semaphore(bound);
    }

    public boolean add(E e) throws InterruptedException {
        semaphore.acquire();
        boolean wasAdded = false;

        try {
            wasAdded = set.add(e);
            return wasAdded;
        } finally {
            if (!wasAdded)
                semaphore.release();
        }
    }

    public boolean remove(Object e) {
        boolean wasRemoved = false;

        try {
            wasRemoved = set.remove(e);
            return wasRemoved;
        } finally {
            if (wasRemoved)
                semaphore.release();
        }
    }

}
