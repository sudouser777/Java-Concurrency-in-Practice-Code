package chapter09;

import java.util.concurrent.*;

public abstract class BackgroundTask<V> implements Runnable, Future<V> {

    private final FutureTask<V> computation = new Computation();

    private class Computation extends FutureTask<V> {
        public Computation() {
            super(BackgroundTask.this::compute);
        }

        @Override
        protected void done() {
            GuiExecutor.getInstance().execute(() -> {
                V value = null;
                Throwable exception = null;
                boolean cancelled = false;
                try {
                    value = get();
                } catch (ExecutionException e) {
                    exception = e.getCause();
                } catch (CancellationException e) {
                    cancelled = true;
                } catch (InterruptedException _) {
                } finally {
                    onCompletion(value, exception, cancelled);
                }
            });
        }
    }

    // Called in the background thread
    protected abstract V compute() throws Exception;

    // Called in the event dispatch thread
    protected void onCompletion(V result, Throwable exception, boolean cancelled) {
    }

    protected void onProgress(int current, int max) {
    }


    @Override
    public void run() {
        computation.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return computation.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return computation.isCancelled();
    }

    @Override
    public boolean isDone() {
        return computation.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return computation.get();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return computation.get(timeout, unit);
    }
}
