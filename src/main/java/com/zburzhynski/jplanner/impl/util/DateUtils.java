package com.zburzhynski.jplanner.impl.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Contains common methods for working with dates.
 * <p/>
 * Date: 27.04.15
 *
 * @author Vladimir Zburzhynski
 */
public final class DateUtils {

    private static final int LAST_DAY_HOUR = 23;
    private static final int LAST_HOUR_MINUTE = 59;
    private static final int LAST_MINUTE_SECOND = 59;
    private static final int LAST_MINUTE_MILLISECOND = 999;

    /**
     * Sets initial time 00:00:00 to date.
     *
     * @param date date to set
     * @return new date
     */
    public static Date setInitialTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Sets final time 23:59:59 to date.
     *
     * @param date date to set
     * @return new date
     */
    public static Date setFinalTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, LAST_DAY_HOUR);
        calendar.set(Calendar.MINUTE, LAST_HOUR_MINUTE);
        calendar.set(Calendar.SECOND, LAST_MINUTE_SECOND);
        calendar.set(Calendar.MILLISECOND, LAST_MINUTE_MILLISECOND);
        return calendar.getTime();
    }

    /**
     * Adds minute to date.
     *
     * @param date date to add
     * @param minute minute amount
     * @return new date
     */
    public static Date addMinuteToDate(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * Adds day do date.
     *
     * @param date date to add
     * @param day day amount
     * @return new date
     */
    public static Date addDayToDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * Adds month to date.
     *
     * @param date  date to add
     * @param month month amount
     * @return new date
     */
    public static Date addMonthToDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        return calendar.getTime();
    }

    /**
     * Adds year to date.
     *
     * @param date date to add
     * @param year year amount
     * @return new date
     */
    public static Date addYearToDate(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
        return calendar.getTime();
    }

}
