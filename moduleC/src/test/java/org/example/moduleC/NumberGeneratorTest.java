package org.example.moduleC;

import org.junit.Test;
import static org.junit.Assert.*;

public class NumberGeneratorTest {

    private NumberGenerator numberGenerator = new NumberGenerator();

    @Test
    public void testGenerateRandomNumbers() {
        int[] numbers = numberGenerator.generateRandomNumbers(5, 1, 10);
        assertEquals(5, numbers.length);
        for (int num : numbers) {
            assertTrue(num >= 1 && num <= 10);
        }
    }

    @Test
    public void testGenerateRandomNumbersInvalidParams() {
        assertEquals(0, numberGenerator.generateRandomNumbers(0, 1, 10).length);
        assertEquals(0, numberGenerator.generateRandomNumbers(5, 10, 5).length);
    }

    @Test
    public void testGenerateRandomNumber() {
        int num = numberGenerator.generateRandomNumber(1, 10);
        assertTrue(num >= 1 && num <= 10);
    }

    @Test
    public void testGenerateRandomNumberInvalidParams() {
        int num = numberGenerator.generateRandomNumber(10, 5);
        assertEquals(10, num);
    }

    @Test
    public void testIsEven() {
        assertTrue(numberGenerator.isEven(2));
        assertTrue(numberGenerator.isEven(4));
        assertFalse(numberGenerator.isEven(1));
        assertFalse(numberGenerator.isEven(3));
    }

    @Test
    public void testIsOdd() {
        assertTrue(numberGenerator.isOdd(1));
        assertTrue(numberGenerator.isOdd(3));
        assertFalse(numberGenerator.isOdd(2));
        assertFalse(numberGenerator.isOdd(4));
    }

    @Test
    public void testFilterEvenNumbers() {
        int[] input = {1, 2, 3, 4, 5, 6};
        int[] expected = {2, 4, 6};
        int[] result = numberGenerator.filterEvenNumbers(input);
        assertEquals(expected.length, result.length);
        for (int num : result) {
            assertTrue(numberGenerator.isEven(num));
        }
    }

    @Test
    public void testFilterEvenNumbersNullOrEmpty() {
        assertEquals(0, numberGenerator.filterEvenNumbers(null).length);
        assertEquals(0, numberGenerator.filterEvenNumbers(new int[]{}).length);
    }
}
