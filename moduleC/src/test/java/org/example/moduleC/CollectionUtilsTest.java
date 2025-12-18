package org.example.moduleC;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 * CollectionUtils类的单元测试
 */
public class CollectionUtilsTest {

    private CollectionUtils collectionUtils = new CollectionUtils();

    /**
     * 测试查找重复元素
     */
    @Test
    public void testFindDuplicates() {
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 3, 3);
        List<Integer> result = collectionUtils.findDuplicates(list);
        assertEquals(2, result.size());
        assertEquals(Arrays.asList(2, 3), result);
    }

    /**
     * 测试列表排序
     */
    @Test
    public void testSortList() {
        List<Integer> list = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        List<Integer> result = collectionUtils.sortList(list);
        assertEquals(Arrays.asList(1, 1, 2, 3, 4, 5, 6, 9), result);
    }

    /**
     * 测试计算列表总和
     */
    @Test
    public void testSumList() {
        assertEquals(0, collectionUtils.sumList(null));
        assertEquals(0, collectionUtils.sumList(Arrays.asList()));
        assertEquals(15, collectionUtils.sumList(Arrays.asList(1, 2, 3, 4, 5)));
        assertEquals(10, collectionUtils.sumList(Arrays.asList(-5, 5, 10)));
    }

    /**
     * 测试检查是否包含重复元素
     */
    @Test
    public void testContainsDuplicate() {
        assertEquals(false, collectionUtils.containsDuplicate(null));
        assertEquals(false, collectionUtils.containsDuplicate(Arrays.asList()));
        assertEquals(false, collectionUtils.containsDuplicate(Arrays.asList(1, 2, 3)));
        assertEquals(true, collectionUtils.containsDuplicate(Arrays.asList(1, 2, 2)));
        assertEquals(true, collectionUtils.containsDuplicate(Arrays.asList(1, 1, 1)));
    }
}
