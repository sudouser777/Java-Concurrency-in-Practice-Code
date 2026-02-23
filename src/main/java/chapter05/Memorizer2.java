package chapter05;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Memorizer2<A, V> implements Computable<A, V> {
    @GuardedBy("this")
    private final ConcurrentMap<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer2(Computable<A, V> c) {
        this.c = c;
    }


    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
