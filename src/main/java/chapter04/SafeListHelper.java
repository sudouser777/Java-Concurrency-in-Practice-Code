package chapter04;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public class SafeListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public boolean putIfAbsent(E element) {
        synchronized (list) {
            boolean absent = !list.contains(element);
            if (absent) {
                list.add(element);
            }
            return absent;
        }
    }
}
