package chapter10;

public class DynamicLockOrderingDeadlock {


    // warning: deadlock-prone!
    public void transferMoney(Account from, Account to, DollarAmount amount) {
        synchronized (from) {
            synchronized (to) {
                if (from.getBalance().compareTo(amount) < 0) {
                    throw new IllegalArgumentException("Insufficient funds");
                }
                from.debit(amount);
                to.credit(amount);
            }
        }
    }









}
