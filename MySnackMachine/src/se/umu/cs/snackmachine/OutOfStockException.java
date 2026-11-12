package se.umu.cs.snackmachine;

public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
