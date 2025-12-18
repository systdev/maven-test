package org.example.moduleC;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class CollectionUtilsTest {

    private CollectionUtils collectionUtils = new CollectionUtils();

    @Test
    public void testFindDuplicates() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 4, 3, 5);
        List<Integer> duplicates = collectionUtils.findDuplicates(numbers);
        assertTrue(duplicates.contains(2));
        assertTrue(duplicates.contains(3));
        assertEquals(2, duplicates.size());
    }

    @Test
    public void testFindDuplicatesNoDuplicates() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(0, collectionUtils.findDuplicates(numbers).size());
    }

    @Test
    public void testFindDuplicatesNullOrEmpty() {
        assertEquals(0, collectionUtils.findDuplicates(null).size());
        assertEquals(0, collectionUtils.findDuplicates(Arrays.asList(1)).size());
    }

    @Test
    public void testSortList() {
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);
        List<Integer> sorted = collectionUtils.sortList(numbers);
        assertEquals(Arrays.asList(1, 2, 5, 8, 9), sorted);
    }

    @Test
    public void testSortListNull() {
        assertEquals(0, collectionUtils.sortList(null).size());
    }

    @Test
    public void testSumList() {
        assertEquals(15, collectionUtils.sumList(Arrays.asList(1, 2, 3, 4, 5)));
        assertEquals(0, collectionUtils.sumList(Arrays.asList(0, 0, 0)));
        assertEquals(10, collectionUtils.sumList(Arrays.asList(10)));
    }

    @Test
    public void testSumListNullOrEmpty() {
        assertEquals(0, collectionUtils.sumList(null));
        assertEquals(0, collectionUtils.sumList(Arrays.asList()));
    }

    @Test
    public void testContainsDuplicate() {
        assertTrue(collectionUtils.containsDuplicate(Arrays.asList(1, 2, 3, 2)));
        assertFalse(collectionUtils.containsDuplicate(Arrays.asList(1, 2, 3, 4, 5)));
        assertFalse(collectionUtils.containsDuplicate(Arrays.asList(1)));
        assertFalse(collectionUtils.containsDuplicate(null));
    }
}
