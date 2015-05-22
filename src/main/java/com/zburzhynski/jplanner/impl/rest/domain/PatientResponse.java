package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Patient response.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class PatientResponse implements Serializable {

    private List<Patient> patients;

    private Integer totalCount;

    /**
     * Adds patient to response.
     *
     * @param patient patient to add
     */
    public void addPatient(Patient patient) {
        if (patients == null) {
            patients = new ArrayList<>();
        }
        patients.add(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
