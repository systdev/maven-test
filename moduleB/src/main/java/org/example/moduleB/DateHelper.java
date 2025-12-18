package org.example.moduleB;

public class DateHelper {
    public boolean isLeapYear(int year) {
        if (year < 0) {
            return false;
        }
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int daysInMonth(int month, int year) {
        if (month < 1 || month > 12) {
            return -1;
        }
        if (year < 0) {
            return -1;
        }

        switch (month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    public boolean isWeekend(int dayOfWeek) {
        return dayOfWeek == 1 || dayOfWeek == 7;
    }

    public int daysBetween(int year1, int month1, int day1, int year2, int month2, int day2) {
        if (!isValidDate(year1, month1, day1) || !isValidDate(year2, month2, day2)) {
            return -1;
        }

        int days1 = totalDaysFromEpoch(year1, month1, day1);
        int days2 = totalDaysFromEpoch(year2, month2, day2);
        return Math.abs(days2 - days1);
    }

    private boolean isValidDate(int year, int month, int day) {
        if (year < 0 || month < 1 || month > 12 || day < 1) {
            return false;
        }
        return day <= daysInMonth(month, year);
    }

    private int totalDaysFromEpoch(int year, int month, int day) {
        int totalDays = 0;
        for (int y = 0; y < year; y++) {
            totalDays += isLeapYear(y) ? 366 : 365;
        }
        for (int m = 1; m < month; m++) {
            totalDays += daysInMonth(m, year);
        }
        return totalDays + day;
    }
}
