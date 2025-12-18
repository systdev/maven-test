package org.example.moduleA;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ArrayHelper类的单元测试
 */
public class ArrayHelperTest {

    private ArrayHelper arrayHelper = new ArrayHelper();

    /**
     * 测试数组求和
     */
    @Test
    public void testSum() {
        assertEquals(6, arrayHelper.sum(new int[]{1, 2, 3}));
        assertEquals(0, arrayHelper.sum(new int[]{0, 0, 0}));
        assertEquals(10, arrayHelper.sum(new int[]{10}));
        assertEquals(0, arrayHelper.sum(null));
        assertEquals(0, arrayHelper.sum(new int[]{}));
    }

    /**
     * 测试查找最大值
     */
    @Test
    public void testFindMax() {
        assertEquals(5, arrayHelper.findMax(new int[]{1, 2, 3, 5, 4}));
        assertEquals(3, arrayHelper.findMax(new int[]{1, 3, 2}));
        assertEquals(-1, arrayHelper.findMax(new int[]{-1, -3, -2}));
    }

    /**
     * 测试查找最小值
     */
    @Test
    public void testFindMin() {
        assertEquals(1, arrayHelper.findMin(new int[]{1, 2, 3, 5, 4}));
        assertEquals(1, arrayHelper.findMin(new int[]{1, 3, 2}));
        assertEquals(-3, arrayHelper.findMin(new int[]{-1, -3, -2}));
    }

    /**
     * 测试计算平均值
     */
    @Test
    public void testAverage() {
        assertEquals(2.0, arrayHelper.average(new int[]{1, 2, 3}), 0.001);
        assertEquals(0.0, arrayHelper.average(new int[]{0, 0, 0}), 0.001);
        assertEquals(5.0, arrayHelper.average(new int[]{5}), 0.001);
        assertEquals(0.0, arrayHelper.average(null), 0.001);
        assertEquals(0.0, arrayHelper.average(new int[]{}), 0.001);
    }
}
