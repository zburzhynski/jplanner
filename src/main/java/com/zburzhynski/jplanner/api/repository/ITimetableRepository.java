package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Timetable service.
 * <p/>
 * Date: 06.09.2015
 *
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ITimetableRepository<T extends IDomain> {

    /**
     * Deletes timetable.
     *
     * @param timetable timetable to delete
     */
    void delete(T timetable);

}
