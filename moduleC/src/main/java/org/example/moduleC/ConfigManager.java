package org.example.moduleC;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private Map<String, String> configMap;

    public ConfigManager() {
        configMap = new HashMap<>();
    }

    public void setConfig(String key, String value) {
        if (key == null || key.isEmpty()) {
            return;
        }
        configMap.put(key, value);
    }

    public String getConfig(String key) {
        if (key == null) {
            return null;
        }
        return configMap.get(key);
    }

    public String getConfig(String key, String defaultValue) {
        String value = getConfig(key);
        return value != null ? value : defaultValue;
    }

    public boolean hasConfig(String key) {
        if (key == null) {
            return false;
        }
        return configMap.containsKey(key);
    }

    public void removeConfig(String key) {
        if (key != null) {
            configMap.remove(key);
        }
    }

    public int getConfigCount() {
        return configMap.size();
    }

    public void clearConfig() {
        configMap.clear();
    }
}
