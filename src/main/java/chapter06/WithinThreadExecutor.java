package chapter06;

import java.util.concurrent.Executor;

public class WithinThreadExecutor implements Executor {

    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
