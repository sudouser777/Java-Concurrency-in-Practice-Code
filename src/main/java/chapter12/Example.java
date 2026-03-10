package chapter12;

import java.util.Random;

public class Example {

    private static final int THRESHOLD = 1000;
    private static java.util.Random random = new Random();

    class Account {
        private int balance;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }

    public synchronized void transferCredits(Account from, Account to, int amount) {
        from.setBalance(from.getBalance() - amount);
        if (random.nextInt() > THRESHOLD) {
            Thread.yield(); // Using yield() to cause context switch
        }

        to.setBalance(to.getBalance() + amount);
    }
}
