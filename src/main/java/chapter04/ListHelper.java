package chapter04;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NotThreadSafe
public class ListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public synchronized boolean putIfAbsent(E element) {
        boolean absent = !list.contains(element);
        if (absent) {
            list.add(element);
        }
        return absent;
    }
}
