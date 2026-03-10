package chapter10;

public class Account {

    private DollarAmount balance;

    public Account() {
        balance = new DollarAmount(0);
    }

    void debit(DollarAmount amount) {
        balance = balance.subtract(amount);
    }

    void credit(DollarAmount amount) {
        balance = balance.add(amount);
    }

    DollarAmount getBalance() {
        return balance;
    }

}