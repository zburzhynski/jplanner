package com.zburzhynski.jplanner.impl.criteria;

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

}
