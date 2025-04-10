package view;

import viewmodel.SettingsViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sidebar {
    private JPanel sidebar;
    private boolean sidebarVisible = true;
    private final Color SIDEBAR_BROWN = new Color(205, 183, 158);
    private final Color BUTTON_HOVER_COLOR = new Color(180, 160, 135);  // Hover effect color for buttons
    private SettingsViewModel settingsViewModel;

    public Sidebar(JFrame parent) {
        settingsViewModel = new SettingsViewModel();
        // Sidebar layout and styling
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, parent.getHeight()));
        sidebar.setBackground(SIDEBAR_BROWN);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(150, 130, 100)));  // Soft border

        // Settings button at the bottom of the sidebar
        JButton settingsButton = createSidebarButton("⚙ Settings");
        settingsButton.addActionListener(e -> showSettings(parent));

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        settingsPanel.setBackground(SIDEBAR_BROWN);
        settingsPanel.add(settingsButton);
        sidebar.add(settingsPanel, BorderLayout.SOUTH);
    }

    // Method to create buttons with hover effects and rounded corners
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(190, 170, 145)); // Slightly darker brown
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 130, 100), 1)); // Soft border
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);  // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(190, 170, 145));  // Reset to original color
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
        sidebar.revalidate();
    }

    // Display settings dialog when the settings button is clicked
    private void showSettings(JFrame parent) {
        JDialog settingsDialog = new JDialog(parent, "Settings", true);
        settingsDialog.setSize(300, 200);
        settingsDialog.setLayout(new BorderLayout());

        JPanel settingsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JCheckBox darkMode = new JCheckBox("Dark Mode", (Boolean) settingsViewModel.getSetting("darkMode"));
        darkMode.addActionListener(e -> settingsViewModel.updateSetting("darkMode", darkMode.isSelected()));

        JSlider fontSize = new JSlider(10, 24, (Integer) settingsViewModel.getSetting("fontSize"));
        fontSize.addChangeListener(e -> settingsViewModel.updateSetting("fontSize", fontSize.getValue()));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> settingsDialog.dispose());

        settingsPanel.add(darkMode);
        settingsPanel.add(new JLabel("Font Size:"));
        settingsPanel.add(fontSize);

        settingsDialog.add(settingsPanel, BorderLayout.CENTER);
        settingsDialog.add(closeButton, BorderLayout.SOUTH);
        settingsDialog.setLocationRelativeTo(parent);
        settingsDialog.setVisible(true);
    }
}
