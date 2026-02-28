package chapter07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;


    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger prime = BigInteger.ONE;
        try {
            while (!Thread.currentThread().isInterrupted())
                queue.put(prime = prime.nextProbablePrime());
        } catch (InterruptedException consumed) {
            // Exit the thread when interrupted
        }
    }

    public void cancel() {
        interrupt();
    }

    static class Driver {

        AtomicInteger count = new AtomicInteger(0);

        boolean needMorePrimes() {
            return count.get() <= 11;
        }

        void consumePrimes() throws InterruptedException {
            BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<>(10);

            PrimeProducer producer = new PrimeProducer(primes);
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
