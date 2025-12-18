package org.example.moduleA;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * StringUtils类的单元测试
 */
public class StringUtilsTest {

    private StringUtils stringUtils = new StringUtils();

    /**
     * 测试字符串是否为空
     */
    @Test
    public void testIsEmpty() {
        assertEquals(true, stringUtils.isEmpty(null));
        assertEquals(true, stringUtils.isEmpty(""));
        assertEquals(false, stringUtils.isEmpty("  "));
        assertEquals(false, stringUtils.isEmpty("test"));
    }

    /**
     * 测试转大写
     */
    @Test
    public void testToUpperCase() {
        assertEquals("ABC", stringUtils.toUpperCase("abc"));
        assertEquals("TEST", stringUtils.toUpperCase("test"));
        assertEquals(null, stringUtils.toUpperCase(null));
    }

    /**
     * 测试转小写
     */
    @Test
    public void testToLowerCase() {
        assertEquals("abc", stringUtils.toLowerCase("ABC"));
        assertEquals("test", stringUtils.toLowerCase("TEST"));
        assertEquals(null, stringUtils.toLowerCase(null));
    }

    /**
     * 测试字符串连接
     */
    @Test
    public void testConcatenate() {
        assertEquals("ab", stringUtils.concatenate("a", "b"));
        assertEquals("abc", stringUtils.concatenate("a", "bc"));
        assertEquals("test", stringUtils.concatenate("test", ""));
        assertEquals("", stringUtils.concatenate(null, null));
        assertEquals("hello", stringUtils.concatenate("hello", null));
    }

    /**
     * 测试字符串长度
     */
    @Test
    public void testStringLength() {
        assertEquals(0, stringUtils.stringLength(null));
        assertEquals(0, stringUtils.stringLength(""));
        assertEquals(4, stringUtils.stringLength("test"));
    }
}
