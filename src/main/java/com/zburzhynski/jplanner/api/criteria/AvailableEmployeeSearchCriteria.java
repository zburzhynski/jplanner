package com.zburzhynski.jplanner.api.criteria;

import java.util.Date;

/**
 * Available employee search criteria.
 * <p/>
 * Date: 08.11.2015
 *
 * @author Vladimir Zburzhynski
 */
public class AvailableEmployeeSearchCriteria {

    private Date startDate;

    private Date endDate;

    private String workplaceId;

    private String doctorId;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(String workplaceId) {
        this.workplaceId = workplaceId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

}
