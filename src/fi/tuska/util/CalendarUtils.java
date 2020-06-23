package fi.tuska.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Tuukka Haapasalo
 * @created Nov 19, 2004
 * 
 * $Id: CalendarUtils.java,v 1.2 2008-09-08 14:39:14 tuska Exp $
 */
public class CalendarUtils {

    /**
     * Returns a properly configured calendar instance for current time
     * 
     * @return the calendar instance
     */
    public static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        if (!calendar.isLenient())
            calendar.setLenient(true);
        return calendar;
    }

    /**
     * Returns a properly configured calendar set to the given date
     * 
     * @param date the date to set the calendar point to
     * @return the calendar
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = getCalendar();
        if (date != null)
            calendar.setTime(date);
        return calendar;
    }

    /**
     * Returns a calendar instance representing a given week in a year
     * 
     * @return the calendar instance
     */
    public static Calendar getWeek(int week, int year) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        return calendar;
    }

    /**
     * Returns the calendar configured to the specified instant in time
     * 
     * @return the calendar
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute,
        int second) {
        Calendar calendar = getCalendar();
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar;
    }

    /**
     * Returns the first moment in time in the given week
     * 
     * @return the first moment in time
     */
    public static Calendar getFirstTimeOfWeek(int year, int week) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Returns the last moment in time in the given week
     * 
     * @return the last moment in time
     */
    public static Calendar getLastTimeOfWeek(int year, int week) {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    /**
     * Returns the first moment in time in the given day
     * 
     * @return the first moment in time
     */
    public static Calendar getFirstTimeOfDay(int year, int month, int day) {
        Calendar calendar = getCalendar(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Returns the last moment in time in the given day
     * 
     * @return the last moment in time
     */
    public static Calendar getLastTimeOfDay(int year, int month, int day) {
        Calendar calendar = getCalendar(year, month, day, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    /**
     * Compares two dates. Returns a positive number is the first date is
     * before the second date, zero if the dates are equal and a negative
     * number if the second date is before the first date
     * 
     * @param first
     * @param second
     * @return the comparison
     */
    public static int compareDate(Date first, Date second) {
        return compareDate(getCalendar(first), getCalendar(second));
    }

    /**
     * Compares two dates. Returns a positive number is the first date is
     * before the second date, zero if the dates are equal and a negative
     * number if the second date is before the first date
     * 
     * @param first
     * @param second
     * @return the comparison
     */
    public static int compareDate(Calendar first, Calendar second) {
        // Compare years
        {
            int fYear = first.get(Calendar.YEAR);
            int sYear = second.get(Calendar.YEAR);
            if (fYear < sYear)
                return 3;
            if (fYear > sYear)
                return -3;
        }
        // Compare months
        {
            int fMonth = first.get(Calendar.MONTH);
            int sMonth = second.get(Calendar.MONTH);
            if (fMonth < sMonth)
                return 2;
            if (fMonth > sMonth)
                return -2;
        }
        // Compare days
        {
            int fDay = first.get(Calendar.DAY_OF_MONTH);
            int sDay = second.get(Calendar.DAY_OF_MONTH);
            if (fDay < sDay)
                return 1;
            if (fDay > sDay)
                return -1;
        }
        // All are equal
        return 0;
    }

}