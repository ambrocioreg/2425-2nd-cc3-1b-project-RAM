package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchBar {
    private JTextField searchBar;
    private final String SEARCH_PLACEHOLDER = "Search...";

    public SearchBar(JFrame parent) {
        searchBar = new JTextField(SEARCH_PLACEHOLDER, 20);
        searchBar.setForeground(Color.GRAY);
        searchBar.setPreferredSize(new Dimension(200, 25));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));

        // Search placeholder behavior
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals(SEARCH_PLACEHOLDER)) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText(SEARCH_PLACEHOLDER);
                }
            }
        });

        searchBar.addActionListener(e -> performSearch(parent));
    }

    public JTextField getTextField() {
        return searchBar;
    }

    private void performSearch(JFrame parent) {
        String query = searchBar.getText().toLowerCase();
        if (query.equals(SEARCH_PLACEHOLDER.toLowerCase())) return;

        JPanel cardPanel = (JPanel) parent.getContentPane().getComponent(2); // Assuming cardPanel is the 3rd component
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton note = (JButton) comp;
                note.setVisible(note.getText().toLowerCase().contains(query));
            }
        }
    }
}
