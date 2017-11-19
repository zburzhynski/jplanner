package com.zburzhynski.jplanner.api.criteria;

import com.zburzhynski.jplanner.api.domain.TimetableStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Timetable search criteria.
 * <p/>
 * Date: 18.11.2017
 *
 * @author Nikita Shevtsov
 */
public class TimetableSearchCriteria implements Serializable {

    private Date startDate;

    private Date endDate;

    private String resourceId;

    private TimetableStatus status;

    private boolean intersectingPeriod;

    private List<String> excludedIds;

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public TimetableStatus getStatus() {
        return status;
    }

    public void setStatus(TimetableStatus status) {
        this.status = status;
    }

    public boolean isIntersectingPeriod() {
        return intersectingPeriod;
    }

    public void setIntersectingPeriod(boolean intersectingPeriod) {
        this.intersectingPeriod = intersectingPeriod;
    }

    /**
     * Gets excluded timetable ids.
     *
     * @return excluded timetable ids
     */
    public List<String> getExcludedIds() {
        if (excludedIds == null) {
            excludedIds = new ArrayList<>();
        }
        return excludedIds;
    }

    public void setExcludedIds(List<String> excludedIds) {
        this.excludedIds = excludedIds;
    }
}
