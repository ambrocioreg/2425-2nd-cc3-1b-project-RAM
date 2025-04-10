package view;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardView extends JPanel {
    private Card card;
    private JLabel titleLabel;
    private JTextArea contentArea;
    private boolean isEditing = false;

    public CardView(Card card) {
        this.card = card;
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        titleLabel = new JLabel(card.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);

        contentArea = new JTextArea(card.getContent());
        contentArea.setEditable(false); // Initially not editable
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        contentArea.setBackground(new Color(245, 245, 245));
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        // Add Mouse Listener for editing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isEditing) {
                    enterEditMode();
                }
            }
        });
    }

    private void enterEditMode() {
        isEditing = true;
        contentArea.setEditable(true);
        contentArea.setBackground(Color.WHITE); // Change background to white for editing
        titleLabel.setText("Editing: " + card.getTitle()); // Change title to indicate edit mode

        // Add a save button at the bottom to save changes
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveContent());
        add(saveButton, BorderLayout.SOUTH);

        // Revalidate and repaint to refresh the layout
        revalidate();
        repaint();
    }

    private void saveContent() {
        card.setContent(contentArea.getText());
        contentArea.setEditable(false); // Stop editing
        contentArea.setBackground(new Color(245, 245, 245)); // Reset background
        titleLabel.setText(card.getTitle()); // Reset title

        // Remove save button
        remove(getComponentCount() - 1);

        // Revalidate and repaint to refresh the layout
        revalidate();
        repaint();

        isEditing = false; // Exit edit mode
    }

    public Card getCard() {
        return card;
    }
}
