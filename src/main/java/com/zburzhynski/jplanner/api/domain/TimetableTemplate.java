package com.zburzhynski.jplanner.api.domain;

/**
 * Timetable template.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum TimetableTemplate {

    DAY_OF_WEEK("timetableTemplate.dayOfWeek"),
    EVEN_DAY("timetableTemplate.evenDay"),
    ODD_DAY("timetableTemplate.oddDay"),
    DAY_OF_MONTH("timetableTemplate.dayOfMonth"),
    ARBITRARY_DATE("timetableTemplate.arbitraryDate");

    private String value;

    private TimetableTemplate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}