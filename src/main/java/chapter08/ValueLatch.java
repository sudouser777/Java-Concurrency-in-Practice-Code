package chapter08;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.CountDownLatch;

@ThreadSafe
public class ValueLatch<T> {

    @GuardedBy("this")
    private T value = null;

    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return done.getCount() == 0;
    }

    public synchronized void setValue(T value) {
        if (!isSet()) {
            this.value = value;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }

}
