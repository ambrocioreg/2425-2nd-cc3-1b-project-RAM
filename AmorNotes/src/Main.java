import view.*;
import viewmodel.*;
import javax.swing.*;

import java.awt.*;

public class Main extends JFrame {
    private JButton toggleSidebarButton;
    private Sidebar sidebar;
    private SearchBar searchBar;
    private Editor editor; // Added Editor instance
    private SettingsViewModel settingsViewModel; // Add SettingsViewModel instance

    public Main() {
        setTitle("Amor Notes");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the window on the screen
        setLocationRelativeTo(null);

        settingsViewModel = new SettingsViewModel(); // Initialize SettingsViewModel

        // Editor
        editor = new Editor(settingsViewModel); // Pass SettingsViewModel to Editor
        add(editor, BorderLayout.CENTER); // Add Editor to the center

        // Sidebar
        sidebar = new Sidebar(this, editor); // Pass the Editor instance to Sidebar
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Control Panel (top bar)
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setBackground(new Color(240, 240, 240)); // Light Gray

        // Left section (sidebar toggle and "Notes" label)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(new Color(240, 240, 240)); // Light Gray

        toggleSidebarButton = new JButton("☰");
        toggleSidebarButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        toggleSidebarButton.setMargin(new Insets(2, 8, 2, 8));
        toggleSidebarButton.addActionListener(e -> sidebar.toggleSidebar());
        leftPanel.add(toggleSidebarButton);

        JLabel notesLabel = new JLabel("AmorNotes");
        notesLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        leftPanel.add(notesLabel);

        controlPanel.add(leftPanel, BorderLayout.WEST);

        // Right section (search bar)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(new Color(240, 240, 240)); // Light Gray

        searchBar = new SearchBar(this);
        searchBar.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rightPanel.add(searchBar.getTextField());

        controlPanel.add(rightPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.NORTH);

        // Removed cardPanel and card-related logic
    }

    public Editor getEditor() {
        return editor; // Provide access to the Editor instance
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
