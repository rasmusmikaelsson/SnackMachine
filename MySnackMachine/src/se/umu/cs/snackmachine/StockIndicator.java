package se.umu.cs.snackmachine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class StockIndicator extends JPanel {
    private final Product product;

    public StockIndicator(Product product) {
        this.product = product;
        setPreferredSize(new Dimension(180, 48));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 10;
            int width = Math.max(0, getWidth() - 20);
            int barY = 5;
            int barHeight = 16;
            double fraction = product.getMaxStock() == 0 ? 0.0
                    : (double) product.getStock() / product.getMaxStock();
            fraction = Math.clamp(fraction, 0.0, 1.0);

            g2.setColor(new Color(225, 229, 235));
            g2.fillRoundRect(x, barY, width, barHeight, 10, 10);

            if (fraction > 0) {
                g2.setColor(fraction <= 0.25
                        ? new Color(210, 85, 65)
                        : (fraction <= 0.50
                                ? new Color(226, 160, 45)
                                : new Color(57, 153, 101)));
                g2.fillRoundRect(x, barY,
                        (int) Math.round(width * fraction), barHeight, 10, 10);
            }

            g2.setColor(new Color(55, 60, 68));
            String label = "Stock: " + product.getStock() + " / " + product.getMaxStock();
            FontMetrics metrics = g2.getFontMetrics();
            g2.drawString(label, x, barY + barHeight + metrics.getAscent() + 5);
        } finally {
            g2.dispose();
        }
    }
}
