package org.example.moduleC;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ConfigManager类的单元测试
 */
class ConfigManagerTest {

    private ConfigManager configManager = new ConfigManager();

    /**
     * 测试获取配置值
     */
    @Test
    void testGetConfig() {
        configManager.setConfig("test.key", "test.value");
        assertEquals("test.value", configManager.getConfig("test.key"));
        assertNull(configManager.getConfig("nonexistent"));
    }

    /**
     * 测试获取配置值（带默认值）
     */
    @Test
    void testGetConfigWithDefault() {
        assertEquals("default", configManager.getConfig("nonexistent", "default"));
        configManager.setConfig("test.key", "actual.value");
        assertEquals("actual.value", configManager.getConfig("test.key", "default"));
    }

    /**
     * 测试设置配置值
     */
    @Test
    void testSetConfig() {
        configManager.setConfig("test.key", "test.value");
        assertEquals("test.value", configManager.getConfig("test.key"));
        configManager.setConfig(null, "value");
        assertNull(configManager.getConfig(null));
    }

    /**
     * 测试删除配置
     */
    @Test
    void testRemoveConfig() {
        configManager.setConfig("test.key", "test.value");
        assertTrue(configManager.hasConfig("test.key"));
        configManager.removeConfig("test.key");
        assertFalse(configManager.hasConfig("test.key"));
        configManager.removeConfig(null);
    }

    /**
     * 测试检查配置是否存在
     */
    @Test
    void testHasConfig() {
        assertFalse(configManager.hasConfig("test.key"));
        configManager.setConfig("test.key", "test.value");
        assertTrue(configManager.hasConfig("test.key"));
        assertFalse(configManager.hasConfig(null));
    }

    /**
     * 测试获取配置数量
     */
    @Test
    void testGetConfigCount() {
        assertEquals(0, configManager.getConfigCount());
        configManager.setConfig("key1", "value1");
        assertEquals(1, configManager.getConfigCount());
        configManager.setConfig("key2", "value2");
        assertEquals(2, configManager.getConfigCount());
        configManager.removeConfig("key1");
        assertEquals(1, configManager.getConfigCount());
    }

    /**
     * 测试清空配置
     */
    @Test
    void testClearConfig() {
        configManager.setConfig("key1", "value1");
        configManager.setConfig("key2", "value2");
        assertEquals(2, configManager.getConfigCount());
        configManager.clearConfig();
        assertEquals(0, configManager.getConfigCount());
        assertFalse(configManager.hasConfig("key1"));
    }
}
