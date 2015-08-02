package com.zburzhynski.jplanner.api.domain;

/**
 * Schedule view type bean.
 * <p/>
 * Date: 01.08.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum ScheduleViewType {

    WORKPLACE("scheduleViewType.workplace"),

    EMPLOYEE("scheduleViewType.employee");

    private String value;

    private ScheduleViewType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
