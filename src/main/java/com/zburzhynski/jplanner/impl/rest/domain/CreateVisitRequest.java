package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Create dental visit request.
 * <p/>
 * Date: 02.06.15
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class CreateVisitRequest implements Serializable {

    private String scheduleId;

    private Patient patient = new Patient();

    private String doctorId;

    private Date visitDate;

    private String complaint;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

}
