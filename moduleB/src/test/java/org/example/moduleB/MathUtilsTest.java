package org.example.moduleB;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MathUtils类的单元测试
 */
class MathUtilsTest {

    private MathUtils mathUtils = new MathUtils();

    /**
     * 测试计算幂次
     */
    @Test
    void testPower() {
        assertEquals(1, mathUtils.power(2, 0));
        assertEquals(2, mathUtils.power(2, 1));
        assertEquals(4, mathUtils.power(2, 2));
        assertEquals(8, mathUtils.power(2, 3));
        assertEquals(9, mathUtils.power(3, 2));
        try {
            mathUtils.power(2, -1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Negative exponent not supported", e.getMessage());
        }
    }

    /**
     * 测试计算阶乘
     */
    @Test
    void testFactorial() {
        assertEquals(1, mathUtils.factorial(0));
        assertEquals(1, mathUtils.factorial(1));
        assertEquals(2, mathUtils.factorial(2));
        assertEquals(6, mathUtils.factorial(3));
        assertEquals(120, mathUtils.factorial(5));
        try {
            mathUtils.factorial(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Negative number not supported", e.getMessage());
        }
    }

    /**
     * 测试判断是否为质数
     */
    @Test
    void testIsPrime() {
        assertEquals(false, mathUtils.isPrime(0));
        assertEquals(false, mathUtils.isPrime(1));
        assertEquals(true, mathUtils.isPrime(2));
        assertEquals(true, mathUtils.isPrime(3));
        assertEquals(true, mathUtils.isPrime(5));
        assertEquals(true, mathUtils.isPrime(7));
        assertEquals(false, mathUtils.isPrime(4));
        assertEquals(false, mathUtils.isPrime(6));
        assertEquals(false, mathUtils.isPrime(9));
    }

    /**
     * 测试计算最大公约数
     */
    @Test
    void testGcd() {
        assertEquals(6, mathUtils.gcd(12, 18));
        assertEquals(5, mathUtils.gcd(10, 5));
        assertEquals(1, mathUtils.gcd(7, 9));
        assertEquals(4, mathUtils.gcd(-12, 8));
        assertEquals(3, mathUtils.gcd(-15, -9));
    }

    /**
     * 测试计算平方根
     */
    @Test
    void testSqrt() {
        assertEquals(0.0, mathUtils.sqrt(0), 0.001);
        assertEquals(1.0, mathUtils.sqrt(1), 0.001);
        assertEquals(2.0, mathUtils.sqrt(4), 0.001);
        assertEquals(3.0, mathUtils.sqrt(9), 0.001);
        assertTrue(mathUtils.sqrt(2) > 1.414 && mathUtils.sqrt(2) < 1.415);
        try {
            mathUtils.sqrt(-1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot calculate square root of negative number", e.getMessage());
        }
    }
}
