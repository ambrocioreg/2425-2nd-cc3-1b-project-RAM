package viewmodel;

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
}
