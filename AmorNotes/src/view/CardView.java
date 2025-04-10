package view;
import model.*;
import javax.swing.*;
import java.awt.*;

public class CardView extends JPanel {
    private Card card;
    private JLabel titleLabel;

    public CardView(Card card) {
        this.card = card;
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        setMaximumSize(new Dimension(150, 50)); // Fixed width and height
        setPreferredSize(new Dimension(150, 50)); // Fixed width and height

        titleLabel = new JLabel(card.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        titleLabel.setVerticalAlignment(SwingConstants.CENTER); // Center-align text vertically
        add(titleLabel, BorderLayout.CENTER);
    }

    public Card getCard() {
        return card;
    }
}
