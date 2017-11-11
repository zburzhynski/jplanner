package com.zburzhynski.jplanner.api.criteria;

import com.zburzhynski.jplanner.api.domain.QuotaType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Intersected quota search criteria.
 * <p/>
 * Date: 11.11.2017
 *
 * @author Vladimir Zburzhynski
 */
public class IntersectedQuotaSearchCriteria implements Serializable {

    private Date startDate;

    private Date endDate;

    private String doctorId;

    private List<QuotaType> types;

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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<QuotaType> getTypes() {
        return types;
    }

    public void setTypes(List<QuotaType> types) {
        this.types = types;
    }

}
