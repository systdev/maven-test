package org.example.moduleA;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHelperTest {

    private ArrayHelper arrayHelper = new ArrayHelper();

    @Test
    public void testSum() {
        assertEquals(6, arrayHelper.sum(new int[]{1, 2, 3}));
        assertEquals(0, arrayHelper.sum(new int[]{0, 0, 0}));
        assertEquals(10, arrayHelper.sum(new int[]{10}));
        assertEquals(0, arrayHelper.sum(null));
        assertEquals(0, arrayHelper.sum(new int[]{}));
    }

    @Test
    public void testFindMax() {
        assertEquals(5, arrayHelper.findMax(new int[]{1, 2, 3, 5, 4}));
        assertEquals(5, arrayHelper.findMax(new int[]{5}));
        assertEquals(-1, arrayHelper.findMax(new int[]{-1, -2, -3}));
        assertEquals(5, arrayHelper.findMax(new int[]{1, 5, 2, 5}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMaxWithEmptyArray() {
        arrayHelper.findMax(new int[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMaxWithNullArray() {
        arrayHelper.findMax(null);
    }

    @Test
    public void testFindMin() {
        assertEquals(1, arrayHelper.findMin(new int[]{1, 2, 3, 5, 4}));
        assertEquals(5, arrayHelper.findMin(new int[]{5}));
        assertEquals(-5, arrayHelper.findMin(new int[]{-1, -2, -3, -5}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMinWithEmptyArray() {
        arrayHelper.findMin(new int[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindMinWithNullArray() {
        arrayHelper.findMin(null);
    }

    @Test
    public void testAverage() {
        assertEquals(2.0, arrayHelper.average(new int[]{1, 2, 3}), 0.001);
        assertEquals(2.5, arrayHelper.average(new int[]{1, 2, 3, 4}), 0.001);
        assertEquals(0.0, arrayHelper.average(null), 0.001);
        assertEquals(0.0, arrayHelper.average(new int[]{}), 0.001);
        assertEquals(5.0, arrayHelper.average(new int[]{5}), 0.001);
    }
}
