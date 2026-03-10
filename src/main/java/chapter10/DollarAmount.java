package chapter10;

public class DollarAmount implements Comparable<DollarAmount> {

    private int value;

    DollarAmount(int amount) {
        this.value = amount;
    }

    @Override
    public int compareTo(DollarAmount o) {
        return Integer.compare(value, o.value);
    }

    public DollarAmount subtract(DollarAmount amount) {
        this.value -= amount.value;
        return this;
    }

    public DollarAmount add(DollarAmount amount) {
        this.value += amount.value;
        return this;
    }
}