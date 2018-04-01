package com.zburzhynski.jplanner.impl.util;

import com.zburzhynski.jplanner.api.domain.DayOfWeek;

import java.text.DateFormat;
import java.text.ParseException;
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
    private static final int EVEN_DIVIDER = 2;

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
     * Gets day name from date.
     *
     * @param date date
     * @return day name
     */
    public static DayOfWeek getDayName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DayOfWeek.getByNumber(calendar.get(Calendar.DAY_OF_WEEK));
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
     * Checks is one date before other date truncated field.
     *
     * @param checkDate check date
     * @param endDate   end date
     * @param truncatedField field from {@link Calendar}.
     * @return true if one date before other, else false
     */
    public static boolean truncatedBefore(Date checkDate, Date endDate, int truncatedField) {
        return org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(checkDate, endDate, truncatedField) < 0;
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
     * Checks is one date after other date truncated field.
     *
     * @param checkDate check date
     * @param endDate   end date
     * @param truncatedField field from {@link Calendar}.
     * @return true if one date after other, else false
     */
    public static boolean truncatedAfter(Date checkDate, Date endDate, int truncatedField) {
        return org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(checkDate, endDate, truncatedField) > 0;
    }

    /**
     * Checks if two date objects are on the same day ignoring time.
     *
     * @param date1 first date
     * @param date2 second date
     * @return true if two dates represent the same day
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(date1, date2);
    }

    /**
     * Checks is two dates overlapped including.
     *
     * @param start1 start date of first range
     * @param end1   end date of first range
     * @param start2 start date of second range
     * @param end2   end date of second range
     * @return true if dates overlapped, else false
     */
    public static boolean isOverlapIncluding(Date start1, Date end1, Date start2, Date end2) {
        return beforeOrEquals(start1, end2) && afterOrEquals(end1, start2);
    }

    /**
     * Checks is two dates overlapped excluding.
     *
     * @param start1 start date of first range
     * @param end1   end date of first range
     * @param start2 start date of second range
     * @param end2   end date of second range
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

    /**
     * Parse date by template.
     *
     * @param date     date
     * @param template template
     * @return date
     */
    public static Date parseDate(String date, String template) {
        try {
            Locale locale = new Locale.Builder().setLanguage(RUSSIAN_LANGUAGE)
                .setRegion(RUSSIAN_REGION).build();
            DateFormat dateFormat = new SimpleDateFormat(template, locale);
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Checks if day even.
     *
     * @param date date to check
     * @return true if day even
     */
    public static boolean isEvenDay(Date date) {
        return extractDay(date) % EVEN_DIVIDER == 0;
    }

    /**
     * Checks if day odd.
     *
     * @param date date to check
     * @return true if day odd
     */
    public static boolean isOddDay(Date date) {
        return !isEvenDay(date);
    }

}
