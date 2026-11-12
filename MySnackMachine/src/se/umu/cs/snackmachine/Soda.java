package se.umu.cs.snackmachine;

public class Soda implements Product {
    private static final int MAX_STOCK = 8;
    private int stock = MAX_STOCK;

    @Override public String getName() { return "Soda"; }
    @Override public int getPrice() { return 18; }
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
