package se.umu.cs.snackmachine;

public class SnackMachine {
    private int balance;
    private int successfulPurchases;
    private int insufficientFundsAttempts;
    private int outOfStockAttempts;

    public void insertCoin(int kronor) {
        if (kronor <= 0) {
            throw new IllegalArgumentException("Coin value has to be a positive integer!");
        }
        balance = Math.addExact(balance, kronor);
    }

    /**
     * Check funding before successful purchase.
     */
    public void buy(Product product)
            throws InsufficientFundsException, OutOfStockException {
        if (balance < product.getPrice()) {
            insufficientFundsAttempts++;
            throw new InsufficientFundsException(
                    "Missing " + (product.getPrice() - balance)
                    + " kr for " + product.getName() + ".");
        }

        try {
            product.dispense();
        } catch (OutOfStockException e) {
            outOfStockAttempts++;
            throw e;
        }

        // Funding will be removed once dispense() has succeeded
        balance -= product.getPrice();
        successfulPurchases++;
    }

    public int getBalance() { return balance; }
    public int getSuccessfulPurchases() { return successfulPurchases; }
    public int getInsufficientFundsAttempts() { return insufficientFundsAttempts; }
    public int getOutOfStockAttempts() { return outOfStockAttempts; }
}
