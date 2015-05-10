package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;

import java.util.List;

/**
 * Schedule event repository interface.
 * <p/>
 * Date: 23.04.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 *
 */
public interface IScheduleRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds schedule events by search criteria.
     *
     * @param searchCriteria {@link ScheduleSearchCriteria} to find
     * @return schedule events
     */
    List<T> findByCriteria(ScheduleSearchCriteria searchCriteria);

    /**
     * Finds schedule events contained in the range.
     *
     * @param containCriteria {@link ScheduleSearchCriteria} to find
     * @return schedule events
     */
    List<T> containByCriteria(ScheduleSearchCriteria containCriteria);

}
