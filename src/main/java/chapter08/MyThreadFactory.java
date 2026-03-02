package chapter08;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String name) {
        this.poolName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }
}
