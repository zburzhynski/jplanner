package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;

import java.util.List;

/**
 * Schedule service interface.
 * <p/>
 * Date: 24.04.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IScheduleService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets schedule events by search criteria.
     *
     * @param searchCriteria {@link ScheduleSearchCriteria} to find
     * @return schedule events
     */
    List<T> getByCriteria(ScheduleSearchCriteria searchCriteria);

}
