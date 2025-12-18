package org.example.moduleC;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigManagerTest {

    private ConfigManager configManager = new ConfigManager();

    @Test
    public void testSetAndGetConfig() {
        configManager.setConfig("key1", "value1");
        assertEquals("value1", configManager.getConfig("key1"));
    }

    @Test
    public void testSetConfigNullKey() {
        configManager.setConfig(null, "value");
        configManager.setConfig("", "value");
        assertEquals(0, configManager.getConfigCount());
    }

    @Test
    public void testGetConfigWithDefault() {
        assertEquals("default", configManager.getConfig("nonexistent", "default"));
        configManager.setConfig("key1", "value1");
        assertEquals("value1", configManager.getConfig("key1", "default"));
    }

    @Test
    public void testHasConfig() {
        configManager.setConfig("key1", "value1");
        assertTrue(configManager.hasConfig("key1"));
        assertFalse(configManager.hasConfig("nonexistent"));
        assertFalse(configManager.hasConfig(null));
    }

    @Test
    public void testRemoveConfig() {
        configManager.setConfig("key1", "value1");
        configManager.removeConfig("key1");
        assertFalse(configManager.hasConfig("key1"));
        assertEquals(0, configManager.getConfigCount());
    }

    @Test
    public void testGetConfigCount() {
        assertEquals(0, configManager.getConfigCount());
        configManager.setConfig("key1", "value1");
        assertEquals(1, configManager.getConfigCount());
        configManager.setConfig("key2", "value2");
        assertEquals(2, configManager.getConfigCount());
    }

    @Test
    public void testClearConfig() {
        configManager.setConfig("key1", "value1");
        configManager.setConfig("key2", "value2");
        configManager.clearConfig();
        assertEquals(0, configManager.getConfigCount());
    }
}
