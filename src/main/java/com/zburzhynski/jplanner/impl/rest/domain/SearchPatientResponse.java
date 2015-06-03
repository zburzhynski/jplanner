package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Search patient response.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class SearchPatientResponse implements Serializable {

    private List<Patient> patients = new ArrayList<>();

    private int totalCount;

    /**
     * Adds patient to response.
     *
     * @param patient patient to add
     */
    public void addPatient(Patient patient) {
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