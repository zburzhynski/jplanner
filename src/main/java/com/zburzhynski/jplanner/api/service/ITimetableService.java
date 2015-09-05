package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.TimetableCreateCriteria;

/**
 * Timetable service interface.
 * <p/>
 * Date: 04.09.2015
 *
 * @author Vladimir Zburzhynski
 */
public interface ITimetableService {

    /**
     * Creates timetable.
     *
     * @param createCriteria {@link TimetableCreateCriteria} timetable create criteria
     */
    void createTimetable(TimetableCreateCriteria createCriteria);

}
