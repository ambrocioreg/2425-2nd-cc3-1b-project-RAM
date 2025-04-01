import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame {
    private JButton toggleSidebarButton, addNoteButton;
    private JTextField searchBar;
    private JPanel cardPanel, sidebar;
    private boolean sidebarVisible = true;
    private ArrayList<Card> cards;
    private final String SEARCH_PLACEHOLDER = "Search...";
    private final Color LIGHT_BROWN = new Color(225, 205, 180);
    private final Color SIDEBAR_BROWN = new Color(205, 183, 158);

    public Main() {
        setTitle("Amor Notes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar with settings button
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBackground(SIDEBAR_BROWN);
        
        // Settings button at bottom of sidebar
        JButton settingsButton = new JButton("⚙ Settings");
        settingsButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        settingsButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        settingsButton.setBackground(new Color(190, 170, 145)); // Slightly darker brown
        settingsButton.setOpaque(true);
        settingsButton.setBorderPainted(false);
        settingsButton.addActionListener(e -> showSettings());
        
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        settingsPanel.setBackground(SIDEBAR_BROWN);
        settingsPanel.add(settingsButton);
        sidebar.add(settingsPanel, BorderLayout.SOUTH);
        
        add(sidebar, BorderLayout.WEST);
        
        // Control Panel (top bar)
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        
        // Sidebar toggle (☰)
        toggleSidebarButton = new JButton("☰");
        toggleSidebarButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        toggleSidebarButton.setMargin(new Insets(2, 8, 2, 8));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(toggleSidebarButton, gbc);
        
        // Search bar
        searchBar = new JTextField(SEARCH_PLACEHOLDER, 20);
        searchBar.setForeground(Color.GRAY);
        searchBar.setPreferredSize(new Dimension(200, 25));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        controlPanel.add(searchBar, gbc);
        
        // Add Note button (➕)
        addNoteButton = new JButton("➕");
        addNoteButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        addNoteButton.setMargin(new Insets(2, 8, 2, 8));
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(addNoteButton, gbc);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // Card Panel (3x3 grid)
        cardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardPanel.setBackground(new Color(240, 235, 230));
        add(cardPanel, BorderLayout.CENTER);
        
        // Search placeholder behavior
        searchBar.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals(SEARCH_PLACEHOLDER)) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText(SEARCH_PLACEHOLDER);
                }
            }
        });
        
        // Event Listeners
        addNoteButton.addActionListener(e -> addCard(new Card("Note " + (cards.size() + 1))));
        toggleSidebarButton.addActionListener(e -> toggleSidebar());
        searchBar.addActionListener(e -> performSearch());
        
        cards = new ArrayList<>();
    }

    private void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebar.setVisible(sidebarVisible);
        sidebar.setPreferredSize(new Dimension(sidebarVisible ? 200 : 0, getHeight()));
        revalidate();
    }

    private void addCard(Card card) {
        if (cards.size() >= 9) {
            JOptionPane.showMessageDialog(this, "Maximum 9 notes allowed.");
            return;
        }
        cards.add(card);
        JButton noteButton = new JButton(card.getTitle());
        noteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        noteButton.setBackground(LIGHT_BROWN);
        noteButton.setOpaque(true);
        noteButton.setBorderPainted(false);
        noteButton.setFocusPainted(false);
        cardPanel.add(noteButton);
        cardPanel.revalidate();
    }

    private void performSearch() {
        String query = searchBar.getText().toLowerCase();
        if (query.equals(SEARCH_PLACEHOLDER.toLowerCase())) return;
        
        for (Component comp : cardPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton note = (JButton) comp;
                note.setVisible(note.getText().toLowerCase().contains(query));
            }
        }
    }

    private void showSettings() {
        JDialog settingsDialog = new JDialog(this, "Settings", true);
        settingsDialog.setSize(300, 200);
        settingsDialog.setLayout(new BorderLayout());
        
        JPanel settingsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Example settings options
        JCheckBox darkMode = new JCheckBox("Dark Mode");
        JSlider fontSize = new JSlider(10, 24, 14);
        JButton closeButton = new JButton("Close");
        
        settingsPanel.add(darkMode);
        settingsPanel.add(new JLabel("Font Size:"));
        settingsPanel.add(fontSize);
        
        closeButton.addActionListener(e -> settingsDialog.dispose());
        
        settingsDialog.add(settingsPanel, BorderLayout.CENTER);
        settingsDialog.add(closeButton, BorderLayout.SOUTH);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}

class Card {
    private String title;
    public Card(String title) { this.title = title; }
    public String getTitle() { return title; }
}