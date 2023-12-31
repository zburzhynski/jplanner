package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Create dental visit response.
 * <p/>
 * Date: 02.06.15
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class CreateVisitResponse implements Serializable {

    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

}
