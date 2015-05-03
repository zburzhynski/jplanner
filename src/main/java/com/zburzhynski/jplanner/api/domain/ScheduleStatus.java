package com.zburzhynski.jplanner.api.domain;

/**
 * Schedule status.
 * <p/>
 * Date: 03.05.15
 *
 * @author Vladimir Zburzhynski
 */
public enum ScheduleStatus {

    PLANNED("scheduleStatus.planned"),
    STARTED("scheduleStatus.started"),
    FINISHED("scheduleStatus.finished");

    private String value;

    private ScheduleStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
