package se.umu.cs.snackmachine;

public class Chips implements Product {
    private static final int MAX_STOCK = 5;
    private int stock = MAX_STOCK;

    @Override public String getName() { return "Chips"; }
    @Override public int getPrice() { return 25; }
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
