package view;
import java.awt.*;
import javax.swing.*;

public class Sidebar {
    private JPanel sidebar;
    private boolean sidebarVisible = true;
    private final Color SIDEBAR_BROWN = new Color(205, 183, 158);

    public Sidebar(JFrame parent) {
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, parent.getHeight()));
        sidebar.setBackground(SIDEBAR_BROWN);

        // Settings button at bottom of sidebar
        JButton settingsButton = new JButton("⚙ Settings");
        settingsButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        settingsButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        settingsButton.setBackground(new Color(190, 170, 145)); // Slightly darker brown
        settingsButton.setOpaque(true);
        settingsButton.setBorderPainted(false);
        settingsButton.addActionListener(e -> showSettings(parent));

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

    private void showSettings(JFrame parent) {
        JDialog settingsDialog = new JDialog(parent, "Settings", true);
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
        settingsDialog.setLocationRelativeTo(parent);
        settingsDialog.setVisible(true);
    }
}
