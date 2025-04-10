package view;

import viewmodel.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Sidebar {
    private JPanel sidebar;
    private JPanel cardListPanel; // Panel to hold the cards
    private ArrayList<Card> cards; // List of cards
    private boolean sidebarVisible = true;
    private final Color SIDEBAR_BACKGROUND = new Color(240, 240, 240); // Light Gray
    private final Color BUTTON_HOVER_COLOR = new Color(220, 220, 220); // Slightly Darker Gray
    private final Color BUTTON_BACKGROUND = new Color(200, 200, 200); // Medium Gray
    private final Color BORDER_COLOR = new Color(180, 180, 180); // Darker Gray for borders
    private SettingsViewModel settingsViewModel;
    private Editor editor; // Reference to the main editor

    public Sidebar(JFrame parent, Editor editor) {
        this.editor = editor; // Initialize the editor reference
        settingsViewModel = new SettingsViewModel();
        cards = new ArrayList<>(); // Initialize the card list

        // Sidebar layout and styling
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(0, parent.getHeight())); // Initial width set to 0 (hidden))
        sidebar.setBackground(SIDEBAR_BACKGROUND);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(150, 130, 100)));  // Soft border
        sidebar.setVisible(false); // Sidebar is hidden by default

        // Card list panel
        cardListPanel = new JPanel();
        cardListPanel.setLayout(new BoxLayout(cardListPanel, BoxLayout.Y_AXIS));
        cardListPanel.setBackground(SIDEBAR_BACKGROUND);
        JScrollPane scrollPane = new JScrollPane(cardListPanel);
        scrollPane.setBorder(null);
        sidebar.add(scrollPane, BorderLayout.CENTER);

        // Add Note button at the top of the sidebar
        JButton addNoteButton = createSidebarButton("+ Add Note");
        addNoteButton.addActionListener(e -> {
            String cardTitle = JOptionPane.showInputDialog(parent, "Enter note title:", "New Note", JOptionPane.PLAIN_MESSAGE);
            if (cardTitle != null && !cardTitle.trim().isEmpty()) {
                Card newCard = new Card(cardTitle.trim());
                addCard(newCard); // Add a new card with the entered title
                editor.loadCard(newCard); // Load the new card into the editor
            }
        });
        JPanel addNotePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addNotePanel.setBackground(SIDEBAR_BACKGROUND);
        addNotePanel.add(addNoteButton);
        sidebar.add(addNotePanel, BorderLayout.NORTH);

        // Settings button at the bottom of the sidebar
        JButton settingsButton = createSidebarButton("Settings");
        settingsButton.addActionListener(e -> showSettings(parent));

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        settingsPanel.setBackground(SIDEBAR_BACKGROUND);
        settingsPanel.add(settingsButton);
        sidebar.add(settingsPanel, BorderLayout.SOUTH);
    }

    // Add a card to the sidebar
    public void addCard(Card card) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(SIDEBAR_BACKGROUND);
        cardPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(150, 130, 100)));
        cardPanel.setMaximumSize(new Dimension(200, 50)); // Fixed width and height
        cardPanel.setPreferredSize(new Dimension(200, 50)); // Fixed width and height

        JLabel cardLabel = new JLabel(card.getTitle());
        cardLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cardLabel.setForeground(Color.BLACK);
        cardLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        cardLabel.setVerticalAlignment(SwingConstants.CENTER); // Center-align text vertically
        cardPanel.add(cardLabel, BorderLayout.CENTER);

        JButton deleteButton = createSidebarButton("x");
        deleteButton.setPreferredSize(new Dimension(30, 30));
        deleteButton.addActionListener(e -> {
            cards.remove(card);
            cardListPanel.remove(cardPanel);
            cardListPanel.revalidate();
            cardListPanel.repaint();
        });
        cardPanel.add(deleteButton, BorderLayout.EAST);

        // Add hover effect to indicate clickability
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setBackground(BUTTON_HOVER_COLOR); // Change background on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.setBackground(SIDEBAR_BACKGROUND); // Reset background on exit
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                editor.saveCard(); // Save the current card's content
                editor.loadCard(card); // Load the clicked card into the editor
                JOptionPane.showMessageDialog(cardPanel, "Card \"" + card.getTitle() + "\" selected.", "Card Clicked", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cardListPanel.add(cardPanel);
        cardListPanel.revalidate();
        cardListPanel.repaint();
    }

    // Method to create buttons with hover effects and rounded corners
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Ensure font is bold and size is appropriate
        button.setForeground(Color.BLACK); // Set text color to black for better visibility
        button.setBackground(BUTTON_BACKGROUND); // Update button background color
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1)); // Update button border color
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);  // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_BACKGROUND);  // Reset to original color
            }
        });
        return button;
    }

    // Return the sidebar panel
    public JPanel getPanel() {
        return sidebar;
    }

    // Toggle the visibility of the sidebar
    public void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebar.setVisible(sidebarVisible);
        sidebar.setPreferredSize(new Dimension(sidebarVisible ? 200 : 0, sidebar.getHeight()));
        sidebar.getParent().revalidate(); // Ensure the parent layout updates
        sidebar.getParent().repaint();   // Repaint the parent container
    }

    // Display settings dialog when the settings button is clicked
    private void showSettings(JFrame parent) {
        JDialog settingsDialog = new JDialog(parent, "Settings", true);
        settingsDialog.setSize(960, 540);
        settingsDialog.setLayout(new BorderLayout());

        JPanel settingsPanel = settingsViewModel.createSettingsPanel(parent); // Delegate to SettingsViewModel
        settingsDialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = settingsDialog.getSize();
                int fontSize = Math.min(30, 10 + (newSize.width / 100)); // Adjust font size proportionally to dialog width
                updateFontSize(settingsPanel, fontSize);
                settingsPanel.revalidate();
            }
        });

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeButton.addActionListener(e -> settingsDialog.dispose());

        // Add panels to the dialog
        settingsDialog.add(settingsPanel, BorderLayout.CENTER);
        settingsDialog.add(closeButton, BorderLayout.SOUTH);
        settingsDialog.setLocationRelativeTo(parent);
        settingsDialog.setVisible(true);
    }

    private void updateFontSize(Component component, int fontSize) {
        if (component instanceof JComponent) {
            ((JComponent) component).setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                updateFontSize(child, fontSize);
            }
        }
    }
}
