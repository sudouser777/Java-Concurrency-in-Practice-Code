package chapter04;

import chapter02.Widget;
import net.jcip.annotations.GuardedBy;

public class PrivateLock {

    private final Object myLock = new Object();

    @GuardedBy("myLock")
    Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
            widget.doSomething();
        }
    }
}
