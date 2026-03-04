package chapter09;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public class SwingUtilities {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor(new SwingThreadFactory());

    private static volatile Thread swingThread;

    private static class SwingThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            swingThread = new Thread(r);
            return swingThread;
        }
    }

    public static boolean isEventDispatchThread() {
        return Thread.currentThread() == swingThread;
    }

    public static void invokeLater(Runnable r) {
        executor.execute(r);
    }

    public static void invokeAndWait(Runnable r) throws InvocationTargetException, InterruptedException {
        Future<?> future = executor.submit(r);
        try {
            future.get();
        } catch (ExecutionException e) {
            throw new InvocationTargetException(e);
        }
    }
}
