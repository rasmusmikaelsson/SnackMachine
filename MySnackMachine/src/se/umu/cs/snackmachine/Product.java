package se.umu.cs.snackmachine;

public interface Product {
    String getName();
    int getPrice();      // Price in Swedish kronor
    int getStock();      // Quantity left in machine
    int getMaxStock();   // Maximum quantity
    void dispense() throws OutOfStockException;
}
