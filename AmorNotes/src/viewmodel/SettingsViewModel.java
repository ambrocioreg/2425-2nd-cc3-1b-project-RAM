package viewmodel;

import javax.swing.*;
import java.awt.*;

public class SettingsViewModel {
    private final JFrame parent;
    private boolean isDarkModeEnabled = false;
    private boolean isAutoSyncEnabled = false;

    public SettingsViewModel(JFrame parent) {
        this.parent = parent;
    }

    public void enableDarkMode() {
        isDarkModeEnabled = true;
        // ...apply dark mode settings...
    }

    public void enableLightMode() {
        isDarkModeEnabled = false;
        // ...apply light mode settings...
    }

    public void toggleAutoSync() {
        isAutoSyncEnabled = !isAutoSyncEnabled;
        // ...apply auto-sync settings...
    }

    public void showSettingsDialog() {
        JDialog settingsDialog = new JDialog(parent, "Settings", true);
        settingsDialog.setSize(300, 200);
        settingsDialog.setLayout(new BorderLayout());

        JPanel settingsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JCheckBox darkMode = new JCheckBox("Dark Mode", isDarkModeEnabled);
        darkMode.addActionListener(e -> {
            if (darkMode.isSelected()) enableDarkMode();
            else enableLightMode();
        });

        JCheckBox autoSync = new JCheckBox("Auto Sync", isAutoSyncEnabled);
        autoSync.addActionListener(e -> toggleAutoSync());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> settingsDialog.dispose());

        settingsPanel.add(darkMode);
        settingsPanel.add(autoSync);
        settingsPanel.add(closeButton);

        settingsDialog.add(settingsPanel, BorderLayout.CENTER);
        settingsDialog.setLocationRelativeTo(parent);
        settingsDialog.setVisible(true);
    }
}
