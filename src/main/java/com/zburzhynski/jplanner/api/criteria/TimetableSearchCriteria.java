package com.zburzhynski.jplanner.api.criteria;

import com.zburzhynski.jplanner.api.domain.TimetableStatus;

/**
 * Timetable search criteria.
 * <p/>
 * Date: 18.11.2017
 *
 * @author Nikita Shevtsov
 */
public class TimetableSearchCriteria {

    private TimetableStatus status;

    public TimetableStatus getStatus() {
        return status;
    }

    public void setStatus(TimetableStatus status) {
        this.status = status;
    }

}
