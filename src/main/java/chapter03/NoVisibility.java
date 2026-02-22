package chapter03;


public class NoVisibility {

    private static boolean ready;

    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    static void main() {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
