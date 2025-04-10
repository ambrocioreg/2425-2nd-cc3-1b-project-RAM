package view;
import java.awt.*;
import javax.swing.*;
import viewmodel.*;

public class Sidebar {
    private JPanel sidebar;
    private boolean sidebarVisible = true;
    private final Color SIDEBAR_BROWN = new Color(205, 183, 158);
    private final SettingsViewModel settingsViewModel; // ViewModel reference

    public Sidebar(JFrame parent) {
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, parent.getHeight()));
        sidebar.setBackground(SIDEBAR_BROWN);

        settingsViewModel = new SettingsViewModel(parent); // Initialize ViewModel

        // Settings button at bottom of sidebar
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        settingsButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        settingsButton.setBackground(new Color(190, 170, 145)); // Slightly darker brown
        settingsButton.setOpaque(true);
        settingsButton.setBorderPainted(false);
        settingsButton.addActionListener(e -> settingsViewModel.showSettingsDialog());

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        settingsPanel.setBackground(SIDEBAR_BROWN);
        settingsPanel.add(settingsButton);
        sidebar.add(settingsPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return sidebar;
    }

    public void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebar.setVisible(sidebarVisible);
        sidebar.setPreferredSize(new Dimension(sidebarVisible ? 200 : 0, sidebar.getHeight()));
        sidebar.revalidate();
    }
}
