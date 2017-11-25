package com.zburzhynski.jplanner.api.criteria;

import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.domain.TimetableStatus;

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
public class QuotaSearchCriteria implements Serializable {

    private Date startDate;

    private Date endDate;

    private String doctorId;

    private String workplaceId;

    private List<TimetableStatus> timetableStatuses;

    private List<QuotaType> types;

    private List<String> excludedResourceIds;

    private boolean intersectingPeriod;

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

    public String getWorkplaceId() {
        return workplaceId;
    }

    public void setWorkplaceId(String workplaceId) {
        this.workplaceId = workplaceId;
    }

    public List<TimetableStatus> getTimetableStatuses() {
        return timetableStatuses;
    }

    public void setTimetableStatuses(List<TimetableStatus> timetableStatuses) {
        this.timetableStatuses = timetableStatuses;
    }

    public List<QuotaType> getTypes() {
        return types;
    }

    public void setTypes(List<QuotaType> types) {
        this.types = types;
    }

    public List<String> getExcludedResourceIds() {
        return excludedResourceIds;
    }

    public void setExcludedResourceIds(List<String> excludedResourceIds) {
        this.excludedResourceIds = excludedResourceIds;
    }

    public boolean isIntersectingPeriod() {
        return intersectingPeriod;
    }

    public void setIntersectingPeriod(boolean intersectingPeriod) {
        this.intersectingPeriod = intersectingPeriod;
    }

}
