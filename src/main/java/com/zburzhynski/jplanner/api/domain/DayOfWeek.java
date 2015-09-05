package com.zburzhynski.jplanner.api.domain;

import java.util.EnumSet;

/**
 * Day of week.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum DayOfWeek {

    MONDAY(1, 2, "dayOfWeek.monday"),
    TUESDAY(2, 3, "dayOfWeek.tuesday"),
    WEDNESDAY(3, 4, "dayOfWeek.wednesday"),
    THURSDAY(4, 5, "dayOfWeek.thursday"),
    FRIDAY(5, 6, "dayOfWeek.friday"),
    SATURDAY(6, 7, "dayOfWeek.saturday"),
    SUNDAY(7, 1, "dayOfWeek.sunday");

    private Integer russianNumber;

    private Integer englishNumber;

    private String name;

    private DayOfWeek(int russianNumber, int englishNumber, String name) {
        this.russianNumber = russianNumber;
        this.englishNumber = englishNumber;
        this.name = name;
    }

    /**
     * Gets day by number.
     *
     * @param dayNumber day number
     * @return day
     */
    public static DayOfWeek getByNumber(int dayNumber) {
        EnumSet<DayOfWeek> days = EnumSet.allOf(DayOfWeek.class);
        for (DayOfWeek day : days) {
            if (day.getEnglishNumber() == dayNumber) {
                return day;
            }
        }
        return null;
    }

    public Integer getRussianNumber() {
        return russianNumber;
    }

    public Integer getEnglishNumber() {
        return englishNumber;
    }

    public String getName() {
        return name;
    }

}
