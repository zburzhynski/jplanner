package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.TimetableCreateCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Timetable service interface.
 * <p/>
 * Date: 04.09.2015
 *
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ITimetableService<T extends IDomain> {

    /**
     * Creates timetable.
     *
     * @param createCriteria {@link TimetableCreateCriteria} timetable create criteria
     */
    void createTimetable(TimetableCreateCriteria createCriteria);

    /**
     * Deletes timetable.
     *
     * @param timetable timetable to delete
     */
    void delete(T timetable);

}
