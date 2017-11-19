package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.criteria.TimetableSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Available resource timetable service.
 * <p/>
 * Date: 06.09.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IResourceTimetableRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds timetables by search criteria.
     *
     * @param searchCriteria {@link TimetableSearchCriteria} to find
     * @return timetables
     */
    List<T> findByCriteria(TimetableSearchCriteria searchCriteria);

    /**
     * Finds count by search criteria.
     *
     * @param searchCriteria {@link TimetableSearchCriteria} to find
     * @return count of timetables
     */
    Integer countByCriteria(TimetableSearchCriteria searchCriteria);

}
