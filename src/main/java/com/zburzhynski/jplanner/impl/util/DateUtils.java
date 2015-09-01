package com.zburzhynski.jplanner.impl.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private static final String DAY_TEMPLATE = "dd";
    private static final String MONTH_TEMPLATE = "MM";
    private static final String YEAR_TEMPLATE = "yyyy";
    private static final String RUSSIAN_LANGUAGE = "ru";
    private static final String RUSSIAN_REGION = "RU";

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
     * @param date   date to add
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
     * @param day  day amount
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

    /**
     * Extracts hour from date.
     *
     * @param date date
     * @return hour from date
     */
    public static int extractHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Extracts day from date.
     *
     * @param date date
     * @return day from date
     */
    public static int extractDay(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DAY_TEMPLATE);
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Extracts month from date.
     *
     * @param date date
     * @return day from date
     */
    public static int extractMonth(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(MONTH_TEMPLATE);
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Extracts year from date.
     *
     * @param date date
     * @return year from date
     */
    public static int extractYear(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YEAR_TEMPLATE);
        return Integer.parseInt(dateFormat.format(date));
    }

    /**
     * Checks is one date before or equals other date.
     *
     * @param checkDate check date
     * @param endDate   end date
     * @return true if one date before other, else false
     */
    public static boolean beforeOrEquals(Date checkDate, Date endDate) {
        return checkDate.equals(endDate) || checkDate.before(endDate);
    }

    /**
     * Checks is one date after or equals other date .
     *
     * @param checkDate check date
     * @param startDate start date
     * @return true if one date after other, else false
     */
    public static boolean afterOrEquals(Date checkDate, Date startDate) {
        return checkDate.equals(startDate) || checkDate.after(startDate);
    }

    /**
     * Checks is two dates overlapped including.
     *
     * @param start1 start date of first range
     * @param end1 end date of first range
     * @param start2 start date of second range
     * @param end2 end date of second range
     * @return true if dates overlapped, else false
     */
    public static boolean isOverlapIncluding(Date start1, Date end1, Date start2, Date end2) {
        return beforeOrEquals(start1, end2) && afterOrEquals(end1, start2);
    }

    /**
     * Checks is two dates overlapped excluding.
     *
     * @param start1 start date of first range
     * @param end1 end date of first range
     * @param start2 start date of second range
     * @param end2 end date of second range
     * @return true if dates overlapped, else false
     */
    public static boolean isOverlapExcluding(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }

    /**
     * Formats date by template.
     *
     * @param date     date
     * @param template template
     * @return formatted date
     */
    public static String formatDate(Date date, String template) {
        Locale locale = new Locale.Builder().setLanguage(RUSSIAN_LANGUAGE)
            .setRegion(RUSSIAN_REGION).build();
        DateFormat dateFormat = new SimpleDateFormat(template, locale);
        return dateFormat.format(date);
    }

}
