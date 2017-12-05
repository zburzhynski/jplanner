package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Update patient request.
 * <p/>
 * Date: 04.12.2017
 *
 * @author Nikita Shevtsov
 */
@XmlRootElement
public class UpdatePatientRequest implements Serializable {

    private String scheduleId;

    private String patientId;

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

}
