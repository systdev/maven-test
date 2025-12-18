package org.example.moduleB;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DateHelper类的单元测试
 */
class DateHelperTest {

    private DateHelper dateHelper = new DateHelper();

    /**
     * 测试闰年判断
     */
    @Test
    void testIsLeapYear() {
        assertEquals(true, dateHelper.isLeapYear(2000));
        assertEquals(true, dateHelper.isLeapYear(2004));
        assertEquals(false, dateHelper.isLeapYear(1900));
        assertEquals(false, dateHelper.isLeapYear(2023));
        assertEquals(false, dateHelper.isLeapYear(-1));
    }

    /**
     * 测试获取月份天数
     */
    @Test
    void testDaysInMonth() {
        assertEquals(31, dateHelper.daysInMonth(1, 2024));
        assertEquals(29, dateHelper.daysInMonth(2, 2024));
        assertEquals(28, dateHelper.daysInMonth(2, 2023));
        assertEquals(30, dateHelper.daysInMonth(4, 2024));
        assertEquals(31, dateHelper.daysInMonth(5, 2024));
        assertEquals(-1, dateHelper.daysInMonth(0, 2024));
        assertEquals(-1, dateHelper.daysInMonth(13, 2024));
        assertEquals(-1, dateHelper.daysInMonth(2, -1));
    }

    /**
     * 测试是否为周末
     */
    @Test
    void testIsWeekend() {
        assertEquals(true, dateHelper.isWeekend(1));
        assertEquals(true, dateHelper.isWeekend(7));
        assertEquals(false, dateHelper.isWeekend(2));
        assertEquals(false, dateHelper.isWeekend(6));
    }

    /**
     * 测试计算两个日期之间的天数
     */
    @Test
    void testDaysBetween() {
        assertEquals(1, dateHelper.daysBetween(2024, 1, 1, 2024, 1, 2));
        assertEquals(0, dateHelper.daysBetween(2024, 1, 1, 2024, 1, 1));
        assertEquals(365, dateHelper.daysBetween(2023, 1, 1, 2024, 1, 1));
        assertEquals(-1, dateHelper.daysBetween(2024, 13, 1, 2024, 1, 2));
        assertEquals(-1, dateHelper.daysBetween(2024, 1, 32, 2024, 1, 2));
    }
}
