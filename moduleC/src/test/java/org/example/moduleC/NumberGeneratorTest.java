package org.example.moduleC;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * NumberGenerator类的单元测试
 */
public class NumberGeneratorTest {

    private NumberGenerator generator = new NumberGenerator();

    /**
     * 测试生成随机整数数组
     */
    @Test
    public void testGenerateRandomNumbers() {
        int[] result = generator.generateRandomNumbers(5, 1, 10);
        assertEquals(5, result.length);
        for (int num : result) {
            assertTrue(num >= 1 && num <= 10);
        }
        assertEquals(0, generator.generateRandomNumbers(0, 1, 10).length);
        assertEquals(0, generator.generateRandomNumbers(5, 10, 5).length);
    }

    /**
     * 测试生成单个随机整数
     */
    @Test
    public void testGenerateRandomNumber() {
        int min = 1;
        int max = 100;
        int result = generator.generateRandomNumber(min, max);
        assertTrue(result >= min);
        assertTrue(result <= max);
        assertEquals(max, generator.generateRandomNumber(max, min));
    }

    /**
     * 测试判断是否为偶数
     */
    @Test
    public void testIsEven() {
        assertEquals(true, generator.isEven(2));
        assertEquals(true, generator.isEven(0));
        assertEquals(true, generator.isEven(-2));
        assertEquals(false, generator.isEven(1));
        assertEquals(false, generator.isEven(-1));
    }

    /**
     * 测试判断是否为奇数
     */
    @Test
    public void testIsOdd() {
        assertEquals(true, generator.isOdd(1));
        assertEquals(true, generator.isOdd(-1));
        assertEquals(false, generator.isOdd(2));
        assertEquals(false, generator.isOdd(0));
        assertEquals(false, generator.isOdd(-2));
    }

    /**
     * 测试过滤偶数
     */
    @Test
    public void testFilterEvenNumbers() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] even = generator.filterEvenNumbers(numbers);
        assertEquals(5, even.length);
        assertEquals(2, even[0]);
        assertEquals(4, even[1]);
        assertEquals(6, even[2]);
        assertEquals(8, even[3]);
        assertEquals(10, even[4]);
        assertEquals(0, generator.filterEvenNumbers(null).length);
        assertEquals(0, generator.filterEvenNumbers(new int[0]).length);
    }
}
