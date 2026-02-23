package chapter05;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Memorizer3<A, V> implements Computable<A, V> {
    @GuardedBy("this")
    private final ConcurrentMap<A, FutureTask<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer3(Computable<A, V> c) {
        this.c = c;
    }


    @Override
    public V compute(final A arg) throws InterruptedException {
        FutureTask<V> future = cache.get(arg);
        if (future == null) {
            future = new FutureTask<>(() -> c.compute(arg));
            cache.put(arg, future);
            future.run(); // call to c.compute happens here
        }
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e);
        }
    }
}
