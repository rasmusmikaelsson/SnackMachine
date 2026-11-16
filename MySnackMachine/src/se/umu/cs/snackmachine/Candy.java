
package se.umu.cs.snackmachine;

public class Candy implements Product {
    private static final int MAX_STOCK = 20;
    private int stock = MAX_STOCK;

    @Override
    public String getName() {
        return "Candy";
    }

    @Override
    public int getPrice() {
        return 15;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public int getMaxStock() {
        return MAX_STOCK;
    }

    @Override
    public void dispense() throws OutOfStockException {
        if (stock == 0) {
            throw new OutOfStockException(getName() + " är slut.");
        }
        stock--;
    }
}
