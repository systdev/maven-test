package org.example.moduleA;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {

    private StringUtils stringUtils = new StringUtils();

    @Test
    public void testToUpperCase() {
        assertEquals("HELLO", stringUtils.toUpperCase("hello"));
        assertEquals("WORLD", stringUtils.toUpperCase("World"));
        assertEquals("TEST123", stringUtils.toUpperCase("test123"));
        assertNull(stringUtils.toUpperCase(null));
    }

    @Test
    public void testToLowerCase() {
        assertEquals("hello", stringUtils.toLowerCase("HELLO"));
        assertEquals("world", stringUtils.toLowerCase("World"));
        assertEquals("test123", stringUtils.toLowerCase("TEST123"));
        assertNull(stringUtils.toLowerCase(null));
    }

    @Test
    public void testStringLength() {
        assertEquals(5, stringUtils.stringLength("hello"));
        assertEquals(0, stringUtils.stringLength(""));
        assertEquals(0, stringUtils.stringLength(null));
    }

    @Test
    public void testConcatenate() {
        assertEquals("helloworld", stringUtils.concatenate("hello", "world"));
        assertEquals("world", stringUtils.concatenate(null, "world"));
        assertEquals("hello", stringUtils.concatenate("hello", null));
        assertEquals("hello", stringUtils.concatenate("hello", ""));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stringUtils.isEmpty(null));
        assertTrue(stringUtils.isEmpty(""));
        assertFalse(stringUtils.isEmpty("hello"));
        assertFalse(stringUtils.isEmpty(" "));
    }
}
