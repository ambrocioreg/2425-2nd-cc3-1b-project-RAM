package view;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CardView extends JPanel {
    private final Card card;

    public CardView(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(200, 100));
        setOpaque(false); // Important for custom rounded painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rounded rectangle
        Shape roundRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30);

        // Background color
        g2.setColor(new Color(225, 205, 180)); // light brown
        g2.fill(roundRect);

        // Draw title
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString(card.getTitle(), 15, 25);

        // Draw content (if needed)
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString(card.getContent(), 15, 45);

        g2.dispose();
    }
}
