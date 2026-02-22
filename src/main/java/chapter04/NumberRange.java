package chapter04;

import java.util.concurrent.atomic.AtomicInteger;

public class NumberRange {

    // INVARIANTS: lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);

    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        // warning -- unsafe check-then-act
        if (i > upper.get()) {
            throw new IllegalArgumentException(String.format("cannot set lower to %d > upper", i));
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        // warning -- unsafe check-then-act
        if (i < lower.get()) {
            throw new IllegalArgumentException(String.format("cannot set upper to %d < lower", i));
        }
        upper.set(i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }

}
