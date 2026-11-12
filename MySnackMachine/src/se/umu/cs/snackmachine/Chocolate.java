package se.umu.cs.snackmachine;

public class Chocolate implements Product {
    private static final int MAX_STOCK = 6;
    private int stock = MAX_STOCK;

    @Override public String getName() { return "Chocolate"; }
    @Override public int getPrice() { return 22; }
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
