package chapter05;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.*;

public class Memorizer<A, V> implements Computable<A, V> {
    @GuardedBy("this")
    private final ConcurrentMap<A, FutureTask<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer(Computable<A, V> c) {
        this.c = c;
    }


    @Override
    public V compute(final A arg) throws InterruptedException {
        while (true) {
            FutureTask<V> future = cache.get(arg);
            if (future == null) {
                FutureTask<V> futureTask = new FutureTask<>(() -> c.compute(arg));
                future = cache.putIfAbsent(arg, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                cache.remove(arg, future);
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e);
            }
        }
    }
}
