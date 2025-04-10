package viewmodel;

import javax.swing.*;
import java.awt.*;

public class SettingsViewModel {
    private boolean toggleUIColor; // true for dark mode, false for light mode
    private boolean sync;          // true for auto-sync enabled, false otherwise
    private int fontSize;          // Font size setting

    public SettingsViewModel() {
        // Initialize default settings
        this.toggleUIColor = false; // Default to dark mode
        this.sync = false;         // Default to auto-sync disabled
        this.fontSize = 14;        // Default font size
    }

    public void enableDarkMode() {
        toggleUIColor = true;
    }

    public void enableLightMode() {
        toggleUIColor = false;
    }

    public void toggleAutoSync() {
        sync = !sync;
    }

    public boolean isDarkModeEnabled() {
        return toggleUIColor;
    }

    public boolean isAutoSyncEnabled() {
        return sync;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getDefaultFontSize() {
        return fontSize; // Return the current font size setting
    }

    public JPanel createSettingsPanel(JFrame parent) {
        JPanel settingsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Adjusted layout to fit all components
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dark mode toggle
        JCheckBox darkMode = new JCheckBox("Dark Mode", isDarkModeEnabled());
        darkMode.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        darkMode.addActionListener(e -> {
            if (darkMode.isSelected()) {
                enableDarkMode();
            } else {
                enableLightMode();
            }
        });

        // Font size input
        JLabel fontSizeLabel = new JLabel("Font Size:");
        fontSizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField fontSizeInput = new JTextField(String.valueOf(getFontSize()), 5);
        fontSizeInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fontSizeInput.addActionListener(e -> {
            try {
                int newFontSize = Integer.parseInt(fontSizeInput.getText());
                if (newFontSize >= 10 && newFontSize <= 24) { // Validate font size range
                    setFontSize(newFontSize);
                } else {
                    JOptionPane.showMessageDialog(parent, "Font size must be between 10 and 24.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Auto-sync toggle
        JCheckBox autoSync = new JCheckBox("AutoSync", isAutoSyncEnabled());
        autoSync.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        autoSync.addActionListener(e -> toggleAutoSync());

        // Add components to the settings panel
        settingsPanel.add(darkMode);
        settingsPanel.add(fontSizeLabel);
        settingsPanel.add(fontSizeInput);
        settingsPanel.add(autoSync);

        return settingsPanel;
    }
}
