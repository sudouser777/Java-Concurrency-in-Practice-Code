package chapter07;

public class WorkerThreadExample extends Thread {

    @Override
    public void run() {
        Throwable thrown = null;

        try {
            while (!isInterrupted()) {
                runTask(getTaskFromWorkQueue());
            }
        } catch (Throwable e) {
            thrown = e;
        } finally {
            threadExited(this, thrown);
        }
    }

    private void threadExited(Thread thread, Throwable thrown) {

    }

    private void runTask(Runnable task) {

    }

    private Runnable getTaskFromWorkQueue() {
        return () -> {
        };
    }
}
