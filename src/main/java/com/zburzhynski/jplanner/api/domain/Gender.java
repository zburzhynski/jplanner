package com.zburzhynski.jplanner.api.domain;

/**
 * Male or Female.
 * <p/>
 * Date: 30.04.15
 *
 * @author Vladimir Zburzhynski
 */
public enum Gender {
    M("gender.male"),
    F("gender.female");

    private String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
