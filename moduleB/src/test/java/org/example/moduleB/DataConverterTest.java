package org.example.moduleB;

import org.junit.Test;
import static org.junit.Assert.*;

public class DataConverterTest {

    private DataConverter dataConverter = new DataConverter();

    @Test
    public void testStringToInt() {
        assertEquals(123, dataConverter.stringToInt("123"));
        assertEquals(0, dataConverter.stringToInt(""));
        assertEquals(0, dataConverter.stringToInt(null));
        assertEquals(0, dataConverter.stringToInt("abc"));
        assertEquals(-5, dataConverter.stringToInt("  -5  "));
    }

    @Test
    public void testStringToDouble() {
        assertEquals(3.14, dataConverter.stringToDouble("3.14"), 0.001);
        assertEquals(0.0, dataConverter.stringToDouble(""), 0.001);
        assertEquals(0.0, dataConverter.stringToDouble(null), 0.001);
        assertEquals(0.0, dataConverter.stringToDouble("abc"), 0.001);
        assertEquals(2.5, dataConverter.stringToDouble("  2.5  "), 0.001);
    }

    @Test
    public void testStringToBoolean() {
        assertTrue(dataConverter.stringToBoolean("true"));
        assertTrue(dataConverter.stringToBoolean("TRUE"));
        assertTrue(dataConverter.stringToBoolean("True"));
        assertTrue(dataConverter.stringToBoolean("yes"));
        assertTrue(dataConverter.stringToBoolean("YES"));
        assertTrue(dataConverter.stringToBoolean("1"));
        assertFalse(dataConverter.stringToBoolean("false"));
        assertFalse(dataConverter.stringToBoolean("no"));
        assertFalse(dataConverter.stringToBoolean("0"));
        assertFalse(dataConverter.stringToBoolean(null));
        assertFalse(dataConverter.stringToBoolean("random"));
    }

    @Test
    public void testIntToBinary() {
        assertEquals("0", dataConverter.intToBinary(0));
        assertEquals("1", dataConverter.intToBinary(1));
        assertEquals("10", dataConverter.intToBinary(2));
        assertEquals("11", dataConverter.intToBinary(3));
        assertEquals("1010", dataConverter.intToBinary(10));
        assertEquals("1111111111", dataConverter.intToBinary(1023));
    }

    @Test
    public void testIntToHex() {
        assertEquals("0", dataConverter.intToHex(0));
        assertEquals("1", dataConverter.intToHex(1));
        assertEquals("A", dataConverter.intToHex(10));
        assertEquals("F", dataConverter.intToHex(15));
        assertEquals("10", dataConverter.intToHex(16));
        assertEquals("FF", dataConverter.intToHex(255));
    }
}
