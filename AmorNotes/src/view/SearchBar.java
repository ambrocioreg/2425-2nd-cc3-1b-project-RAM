package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchBar {
    private JTextField searchBar;
    private final String SEARCH_PLACEHOLDER = "Search...";

    public SearchBar(JFrame parent) {
        // Initialize the search bar with enhanced styling
        searchBar = new JTextField(SEARCH_PLACEHOLDER, 20);
        searchBar.setForeground(Color.BLACK); // Set text color to black
        searchBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchBar.setPreferredSize(new Dimension(250, 30)); // Slightly larger for better usability
        
        // Stylish border and padding for rounded corners and subtle effects
        searchBar.setBackground(Color.WHITE); // White background
        searchBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1), // Medium Gray border
            BorderFactory.createEmptyBorder(8, 10, 8, 10)  // Padding
        ));
        searchBar.setCaretColor(Color.BLACK); // Black cursor

        // Search placeholder behavior with smoother transition
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals(SEARCH_PLACEHOLDER)) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK); // Ensure text remains black when focused
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(new Color(150, 150, 150)); // Gray for placeholder
                    searchBar.setText(SEARCH_PLACEHOLDER);
                }
            }
        });

        // Add action listener for search functionality
        searchBar.addActionListener(e -> performSearch(parent));
    }

    // Getter for the text field
    public JTextField getTextField() {
        return searchBar;
    }

    // Perform the search operation based on the query
    private void performSearch(JFrame parent) {
        String query = searchBar.getText().trim().toLowerCase();
        if (query.equals(SEARCH_PLACEHOLDER.toLowerCase()) || query.isEmpty()) return;

        JPanel cardPanel = (JPanel) parent.getContentPane().getComponent(1); // Ensure correct component index for cardPanel
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof JPanel) { // Check if the component is a JPanel (individual card container)
                JPanel cardContainer = (JPanel) comp;
                for (Component innerComp : cardContainer.getComponents()) {
                    if (innerComp instanceof CardView) {
                        CardView cardView = (CardView) innerComp;
                        String cardTitle = cardView.getCard().getTitle().toLowerCase();
                        cardContainer.setVisible(cardTitle.contains(query)); // Update visibility of the card container
                    }
                }
            }
        }
    }
}

