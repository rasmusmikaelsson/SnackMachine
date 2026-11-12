package se.umu.cs.snackmachine;

public class Water implements Product {
    private static final int MAX_STOCK = 10;
    private int stock = MAX_STOCK;

    @Override public String getName() { return "Water"; }
    @Override public int getPrice() { return 12; }
    @Override public int getStock() { return stock; }
    @Override public int getMaxStock() { return MAX_STOCK; }

    @Override
    public void dispense() throws OutOfStockException {
        if (stock == 0) {
            throw new OutOfStockException(getName() + " out of stock.");
        }
        stock--;
    }
}
