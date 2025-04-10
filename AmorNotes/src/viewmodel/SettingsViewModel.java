package viewmodel;

import java.util.HashMap;
import java.util.Map;

public class SettingsViewModel {
    private Map<String, Object> settings;

    public SettingsViewModel() {
        settings = new HashMap<>();
        // Initialize default settings
        settings.put("darkMode", true);
        settings.put("fontSize", 14);
    }

    public Object getSetting(String key) {
        return settings.getOrDefault(key, null);
    }

    public void updateSetting(String key, Object value) {
        settings.put(key, value);
    }

    public void resetSettings() {
        settings.put("darkMode", false);

    }
}
