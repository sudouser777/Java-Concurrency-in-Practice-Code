package chapter07;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class PrimeGenerator implements Runnable {

    private volatile boolean cancelled;

    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList<>();

    @Override
    public void run() {
        BigInteger prime = BigInteger.ONE;
        while (!cancelled) {
            prime = prime.nextProbablePrime();
            synchronized (this) {
                primes.add(prime);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }

    static class Driver {
        List<BigInteger> aSecondOfPrimes() throws InterruptedException {
            PrimeGenerator generator = new PrimeGenerator();
            new Thread(generator).start();
            try {
                Thread.sleep(1000);
            } finally {
                generator.cancel();
            }
            return generator.get();
        }

        static void main() {
            Driver driver = new Driver();
            List<BigInteger> primes = null;
            try {
                primes = driver.aSecondOfPrimes();
            } catch (InterruptedException ignored) {
            }
            System.out.println(primes);
        }
    }
}
