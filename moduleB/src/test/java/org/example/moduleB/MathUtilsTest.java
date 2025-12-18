package org.example.moduleB;

import org.junit.Test;
import static org.junit.Assert.*;

public class MathUtilsTest {

    private MathUtils mathUtils = new MathUtils();

    @Test
    public void testPower() {
        assertEquals(8, mathUtils.power(2, 3));
        assertEquals(1, mathUtils.power(5, 0));
        assertEquals(9, mathUtils.power(3, 2));
        assertEquals(4, mathUtils.power(2, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPowerNegativeExponent() {
        mathUtils.power(2, -1);
    }

    @Test
    public void testFactorial() {
        assertEquals(1, mathUtils.factorial(0));
        assertEquals(1, mathUtils.factorial(1));
        assertEquals(2, mathUtils.factorial(2));
        assertEquals(6, mathUtils.factorial(3));
        assertEquals(120, mathUtils.factorial(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactorialNegative() {
        mathUtils.factorial(-1);
    }

    @Test
    public void testIsPrime() {
        assertFalse(mathUtils.isPrime(0));
        assertFalse(mathUtils.isPrime(1));
        assertTrue(mathUtils.isPrime(2));
        assertTrue(mathUtils.isPrime(3));
        assertTrue(mathUtils.isPrime(5));
        assertTrue(mathUtils.isPrime(7));
        assertFalse(mathUtils.isPrime(4));
        assertFalse(mathUtils.isPrime(9));
    }

    @Test
    public void testGcd() {
        assertEquals(5, mathUtils.gcd(10, 5));
        assertEquals(3, mathUtils.gcd(9, 6));
        assertEquals(1, mathUtils.gcd(7, 5));
        assertEquals(4, mathUtils.gcd(-8, 12));
    }

    @Test
    public void testSqrt() {
        assertEquals(0.0, mathUtils.sqrt(0), 0.001);
        assertEquals(1.0, mathUtils.sqrt(1), 0.001);
        assertEquals(2.0, mathUtils.sqrt(4), 0.001);
        assertEquals(3.0, mathUtils.sqrt(9), 0.001);
        assertEquals(10.0, mathUtils.sqrt(100), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSqrtNegative() {
        mathUtils.sqrt(-1);
    }
}
