package chapter04;

import net.jcip.annotations.ThreadSafe;

import java.util.Vector;

@ThreadSafe
public class BetterVector<E> extends Vector<E> {

    public synchronized boolean putIfAbsent(E element) {
        boolean absent = !contains(element);
        if (absent) {
            add(element);
        }
        return absent;
    }
}
