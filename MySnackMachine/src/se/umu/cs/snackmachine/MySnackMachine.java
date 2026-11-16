package se.umu.cs.snackmachine;

import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.border.EmptyBorder;

public class MySnackMachine extends JFrame {
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final int PRODUCT_COLUMNS = 2;
    private static final int PRODUCT_GAP = 12;
    private static final int PRODUCT_CARD_HEIGHT = 120;
    private static final int PRODUCT_VIEW_HEIGHT = 310;

    private final SnackMachine machine = new SnackMachine();
    private final JLabel balanceLabel = new JLabel();
    private final JTextArea receiptLog = new JTextArea();
    private int receiptNumber;

    private MySnackMachine(List<Product> products) {
        super("MySnackMachine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 560));
        setSize(820, 660);

        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(content);

        JPanel productGrid = new JPanel(
                new GridLayout(0, PRODUCT_COLUMNS, PRODUCT_GAP, PRODUCT_GAP));
        for (Product product : products) {
            productGrid.add(createProductCard(product));
        }

        int rows = (products.size() + PRODUCT_COLUMNS - 1) / PRODUCT_COLUMNS;
        int gridHeight = rows * PRODUCT_CARD_HEIGHT
                + Math.max(0, rows - 1) * PRODUCT_GAP;
        productGrid.setPreferredSize(new Dimension(600, gridHeight));

        JScrollPane productScrollPane = new JScrollPane(productGrid);
        productScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        productScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setPreferredSize(
                new Dimension(600, Math.min(PRODUCT_VIEW_HEIGHT, gridHeight + 4)));
        productScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        productScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        JPanel moneyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        updateBalanceLabel();
        moneyPanel.add(balanceLabel);
        for (int coin : new int[] {1, 5, 10}) {
            JButton coinButton = new JButton("+" + coin + " kr");
            coinButton.addActionListener(event -> {
                machine.insertCoin(coin);
                updateBalanceLabel();
            });
            moneyPanel.add(coinButton);
        }
        controlPanel.add(moneyPanel, BorderLayout.CENTER);

        JButton summaryButton = new JButton("Summary");
        summaryButton.addActionListener(event -> showSummary());
        controlPanel.add(summaryButton, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(productGrid, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.SOUTH);
        content.add(topPanel, BorderLayout.NORTH);

        receiptLog.setEditable(false);
        receiptLog.setLineWrap(true);
        receiptLog.setWrapStyleWord(true);
        receiptLog.setRows(10);
        receiptLog.setBorder(new EmptyBorder(8, 8, 8, 8));
        JScrollPane scrollPane = new JScrollPane(receiptLog);
        JPanel logPanel = new JPanel(new BorderLayout(4, 4));
        logPanel.add(new JLabel("Receipt Log"), BorderLayout.NORTH);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        content.add(logPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel(new BorderLayout(6, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                new EmptyBorder(12, 12, 12, 12)));

        JButton button = new JButton(product.getName() + " — " + product.getPrice() + " kr");
        StockIndicator indicator = new StockIndicator(product);
        button.addActionListener(event -> attemptPurchase(product, indicator));
        card.add(button, BorderLayout.CENTER);
        card.add(indicator, BorderLayout.SOUTH);
        return card;
    }

    private void attemptPurchase(Product product, StockIndicator indicator) {
        try {
            machine.buy(product);
            appendReceipt("APPROVED: " + product.getName() + " ("
                    + product.getPrice() + " kr). Remaining amount: "
                    + machine.getBalance() + " kr.");
            indicator.repaint();
        } catch (InsufficientFundsException | OutOfStockException e) {
            appendReceipt("DENIED – " + e.getClass().getSimpleName()
                    + ": " + e.getMessage());
        } finally {
            updateBalanceLabel();
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Deposited amount: " + machine.getBalance() + " kr");
    }

    private void appendReceipt(String message) {
        receiptNumber++;
        receiptLog.append(String.format("#%03d [%s] %s%n",
                receiptNumber, LocalTime.now().format(TIME_FORMAT), message));
        receiptLog.setCaretPosition(receiptLog.getDocument().getLength());
    }

    private void showSummary() {
        String summary = "Successful purchases: " + machine.getSuccessfulPurchases()
                + "\nDenied – not enough funding: " + machine.getInsufficientFundsAttempts()
                + "\nDenied – out of stock: " + machine.getOutOfStockAttempts();
        JOptionPane.showMessageDialog(this, summary,
                "Summary since start", JOptionPane.INFORMATION_MESSAGE);
    }

    static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        List<Product> products = List.of(
                new Soda(),
                new Chips(),
                new Chocolate(),
                new Water());
        SwingUtilities.invokeLater(() -> new MySnackMachine(products).setVisible(true));
    }
}
