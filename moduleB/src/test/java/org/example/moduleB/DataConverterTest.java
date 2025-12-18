package org.example.moduleB;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * DataConverter类的单元测试
 */
public class DataConverterTest {

    private DataConverter converter = new DataConverter();

    /**
     * 测试字符串转整数
     */
    @Test
    public void testStringToInt() {
        assertEquals(123, converter.stringToInt("123"));
        assertEquals(-456, converter.stringToInt("-456"));
        assertEquals(0, converter.stringToInt("abc"));
        assertEquals(0, converter.stringToInt(null));
        assertEquals(0, converter.stringToInt(""));
    }

    /**
     * 测试字符串转boolean
     */
    @Test
    public void testStringToBoolean() {
        assertEquals(true, converter.stringToBoolean("true"));
        assertEquals(true, converter.stringToBoolean("yes"));
        assertEquals(true, converter.stringToBoolean("1"));
        assertEquals(true, converter.stringToBoolean("TRUE"));
        assertEquals(false, converter.stringToBoolean("false"));
        assertEquals(false, converter.stringToBoolean(null));
        assertEquals(false, converter.stringToBoolean("no"));
    }

    /**
     * 测试整数转二进制字符串
     */
    @Test
    public void testIntToBinary() {
        assertEquals("0", converter.intToBinary(0));
        assertEquals("1010", converter.intToBinary(10));
        assertEquals("1111111111", converter.intToBinary(1023));
        assertEquals("1010", converter.intToBinary(-10));
    }

    /**
     * 测试整数转十六进制字符串
     */
    @Test
    public void testIntToHex() {
        assertEquals("0", converter.intToHex(0));
        assertEquals("A", converter.intToHex(10));
        assertEquals("FF", converter.intToHex(255));
        assertEquals("ABC", converter.intToHex(2748));
    }
}
