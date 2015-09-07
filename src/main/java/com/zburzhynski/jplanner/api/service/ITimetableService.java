package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.TimetableCreateCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Timetable service interface.
 * <p/>
 * Date: 04.09.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ITimetableService<ID, T extends IDomain> {

    /**
     * Creates timetable.
     *
     * @param createCriteria {@link TimetableCreateCriteria} timetable create criteria
     */
    void createTimetable(TimetableCreateCriteria createCriteria);

    /**
     * Gets timetable by id.
     *
     * @param id id
     * @return timetable
     */
    T getById(ID id);

    /**
     * Deletes timetable.
     *
     * @param timetable timetable to delete
     */
    void delete(T timetable);

}
