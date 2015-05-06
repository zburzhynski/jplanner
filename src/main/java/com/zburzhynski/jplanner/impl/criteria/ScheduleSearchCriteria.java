package com.zburzhynski.jplanner.impl.criteria;

import com.zburzhynski.jplanner.impl.domain.Workplace;

import java.util.Date;

/**
 * Schedule search criteria.
 * <p/>
 * Date: 26.04.15
 *
 * @author Vladimir Zburzhynski
 */
public class ScheduleSearchCriteria {

    private Date startDate;

    private Date endDate;

    private Workplace workplace;

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

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

}
