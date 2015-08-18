package com.zburzhynski.jplanner.api.domain;

/**
 * Quota type.
 * <p/>
 * Date: 8/18/2015
 *
 * @author Vladimir Zburzhynski
 */
public enum QuotaType {

    WORK_TIME("quotaType.workTime"),

    OFF_TIME("quotaType.offTime"),

    LUNCH("quotaType.lunch"),

    WEEKEND("quotaType.weekend"),

    HOLIDAY("quotaType.holiday"),

    ILLNESS("quotaType.illness"),

    BUSINESS_TRIP("quotaType.businessTrip"),

    HOOKY("quotaType.hooky");

    private String value;

    private QuotaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
