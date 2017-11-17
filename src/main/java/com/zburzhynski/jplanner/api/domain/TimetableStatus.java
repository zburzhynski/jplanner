package com.zburzhynski.jplanner.api.domain;

/**
 * Class represents statuses of timetable.
 * <p/>
 * Date: 16.11.2017
 *
 * @author Nikita Shevtsov
 */
public enum TimetableStatus {

    DRAFT("timetableStatus.draft"),
    APPROVED("timetableStatus.approved");

    private String value;

    /**
     * Constructor.
     *
     * @param value status value
     */
    TimetableStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
