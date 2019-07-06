package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;

/**
 * Employee.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
public class EmployeeDto extends PersonDto implements Serializable {

    private String id;

    private PositionDto position;

    private String email;

    private String additionalInformation;

    private Boolean actual;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PositionDto getPosition() {
        return position;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

}
