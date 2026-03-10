package chapter10;

import java.util.Random;

public class DemonstrateDeadlock {

    private static final int NUM_THREADS = 20;

    private static final int NUM_ACCOUNTS = 5;

    private static final int NUM_ITERATIONS = 1_000_000;

    static void transferMoney(Account from, Account to, DollarAmount amount) {
        synchronized (from) {
            synchronized (to) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }


    static void main() {
        final Random random = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];

        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts[i] = new Account();
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int from = random.nextInt(NUM_ACCOUNTS);
                    int to = random.nextInt(NUM_ACCOUNTS);
                    DollarAmount amount = new DollarAmount(random.nextInt(1000));
                    transferMoney(accounts[from], accounts[to], amount);
                }
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }
}
