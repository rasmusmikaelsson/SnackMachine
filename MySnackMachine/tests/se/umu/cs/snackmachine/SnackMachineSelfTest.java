package se.umu.cs.snackmachine;

/** Små tester som kan köras utan externa testbibliotek. */
public class SnackMachineSelfTest {
    public static void main(String[] args) throws Exception {
        SnackMachine machine = new SnackMachine();
        Product chips = new Chips();
        int stockBefore = chips.getStock();

        try {
            machine.buy(chips);
            throw new AssertionError("InsufficientFundsException förväntades");
        } catch (InsufficientFundsException expected) {
            check(machine.getBalance() == 0, "Saldo får inte ändras");
            check(chips.getStock() == stockBefore, "Lager får inte ändras");
        }

        machine.insertCoin(10);
        machine.insertCoin(10);
        machine.insertCoin(5);
        machine.buy(chips);
        check(machine.getBalance() == 0, "Köpet ska dra 25 kr");
        check(chips.getStock() == stockBefore - 1, "Köpet ska minska lagret");

        // Köp upp resterande lager, försök därefter köpa en slut vara.
        for (int i = 1; i < stockBefore; i++) {
            machine.insertCoin(25);
            machine.buy(chips);
        }
        check(chips.getStock() == 0, "Varan ska vara slut");
        machine.insertCoin(25);
        try {
            machine.buy(chips);
            throw new AssertionError("OutOfStockException förväntades");
        } catch (OutOfStockException expected) {
            check(machine.getBalance() == 25, "Misslyckat köp får inte dra pengar");
            check(chips.getStock() == 0, "Tomt lager får inte bli negativt");
        }

        check(machine.getSuccessfulPurchases() == stockBefore, "Fel antal lyckade köp");
        check(machine.getInsufficientFundsAttempts() == 1, "Fel antal saldonekningar");
        check(machine.getOutOfStockAttempts() == 1, "Fel antal lagernekningar");
        System.out.println("Samtliga modelltester passerade.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
