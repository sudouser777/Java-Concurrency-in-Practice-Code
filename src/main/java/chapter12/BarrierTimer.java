package chapter12;

public class BarrierTimer implements Runnable {

    private boolean started;

    private long startTime;

    private long endTime;

    @Override
    public void run() {
        long time = System.nanoTime();
        if (!started) {
            started = true;
            startTime = time;
        } else {
            endTime = time;
        }
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }
}
