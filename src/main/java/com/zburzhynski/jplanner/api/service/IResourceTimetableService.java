package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.QuotaCreateCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;

/**
 * Available resource timetable service interface.
 * <p/>
 * Date: 04.09.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IResourceTimetableService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Checks schedules of timetable.
     *
     * @param timetable timetable to check
     * @return true if has schedules, else false
     */
    boolean hasSchedules(ResourceTimetable timetable);

    /**
     * Creates timetable quotas.
     *
     * @param createCriteria {@link QuotaCreateCriteria} quota create criteria
     */
    void createQuota(QuotaCreateCriteria createCriteria);

}
