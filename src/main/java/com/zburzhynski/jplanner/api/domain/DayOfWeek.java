package com.zburzhynski.jplanner.api.domain;

/**
 * Day of week.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum DayOfWeek {

    MONDAY(1, "dayOfWeek.monday"),
    TUESDAY(2, "dayOfWeek.tuesday"),
    WEDNESDAY(3, "dayOfWeek.wednesday"),
    THURSDAY(4, "dayOfWeek.thursday"),
    FRIDAY(5, "dayOfWeek.friday"),
    SATURDAY(6, "dayOfWeek.saturday"),
    SUNDAY(7, "dayOfWeek.sunday");

    private Integer number;

    private String name;

    private DayOfWeek(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}
