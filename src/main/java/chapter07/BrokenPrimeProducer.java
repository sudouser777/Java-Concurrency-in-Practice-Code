package chapter07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BrokenPrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;


    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger prime = BigInteger.ONE;
        while (!cancelled) {
            try {
                queue.put(prime = prime.nextProbablePrime());
            } catch (InterruptedException consumed) {
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    static class Driver {

        AtomicInteger count = new AtomicInteger(0);

        boolean needMorePrimes() {
            return count.get() <= 11;
        }

        void consumePrimes() throws InterruptedException {
            BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<>(10);

            BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
            producer.start();

            try {
                while (needMorePrimes()) {
                    Thread.sleep(5); // Slow down consumer
                    consume(primes.take());
                }
            } finally {
                producer.cancel();
            }
        }

        private void consume(BigInteger prime) {
            count.incrementAndGet();
            System.out.println(prime + " " + count);
        }

        static void main() {
            Driver driver = new Driver();
            try {
                driver.consumePrimes();
            } catch (InterruptedException _) {

            }
        }
    }

}
