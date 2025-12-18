package org.example.moduleB;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateHelperTest {

    private DateHelper dateHelper = new DateHelper();

    @Test
    public void testIsLeapYear() {
        assertTrue(dateHelper.isLeapYear(2000));
        assertTrue(dateHelper.isLeapYear(2020));
        assertTrue(dateHelper.isLeapYear(2024));
        assertFalse(dateHelper.isLeapYear(1900));
        assertFalse(dateHelper.isLeapYear(2021));
        assertFalse(dateHelper.isLeapYear(-1));
    }

    @Test
    public void testDaysInMonth() {
        assertEquals(31, dateHelper.daysInMonth(1, 2020));
        assertEquals(29, dateHelper.daysInMonth(2, 2020));
        assertEquals(28, dateHelper.daysInMonth(2, 2021));
        assertEquals(30, dateHelper.daysInMonth(4, 2020));
        assertEquals(-1, dateHelper.daysInMonth(13, 2020));
        assertEquals(-1, dateHelper.daysInMonth(-1, 2020));
    }

    @Test
    public void testIsWeekend() {
        assertTrue(dateHelper.isWeekend(1));
        assertTrue(dateHelper.isWeekend(7));
        assertFalse(dateHelper.isWeekend(2));
        assertFalse(dateHelper.isWeekend(6));
    }

    @Test
    public void testDaysBetween() {
        assertEquals(366, dateHelper.daysBetween(2020, 1, 1, 2021, 1, 1));
        assertEquals(0, dateHelper.daysBetween(2020, 1, 1, 2020, 1, 1));
        assertEquals(1, dateHelper.daysBetween(2020, 1, 1, 2020, 1, 2));
    }
}
