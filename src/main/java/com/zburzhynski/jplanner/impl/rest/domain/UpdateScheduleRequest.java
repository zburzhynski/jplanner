package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Update schedule request.
 * <p/>
 * Date: 04.12.2017
 *
 * @author Nikita Shevtsov
 */
@XmlRootElement
public class UpdateScheduleRequest implements Serializable {

    private String scheduleId;

    private String patientId;

    private String scheduleStatus;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

}
