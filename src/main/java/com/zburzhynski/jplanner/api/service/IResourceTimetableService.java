package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.QuotaCreateCriteria;
import com.zburzhynski.jplanner.api.criteria.TimetableSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.dto.response.CreateQuotaResponse;

import java.util.List;

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
     * Gets timetables by search criteria.
     *
     * @param searchCriteria {@link TimetableSearchCriteria} to find
     * @return timetables
     */
    List<T> getByCriteria(TimetableSearchCriteria searchCriteria);

    /**
     * Gets count by search criteria.
     *
     * @param searchCriteria {@link TimetableSearchCriteria} to find
     * @return count of timetables
     */
    Integer countByCriteria(TimetableSearchCriteria searchCriteria);

    /**
     * Creates timetable quotas.
     *
     * @param createCriteria {@link QuotaCreateCriteria} quota create criteria
     * @return create quota response
     */
    CreateQuotaResponse createQuota(QuotaCreateCriteria createCriteria);

}
