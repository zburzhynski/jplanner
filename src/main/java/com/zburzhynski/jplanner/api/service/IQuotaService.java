package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.QuotaSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.impl.domain.Quota;

import java.util.Date;
import java.util.List;

/**
 * Quota service.
 * <p/>
 * Date: 02.11.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IQuotaService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets quotas by search criteria.
     *
     * @param searchCriteria {@link QuotaSearchCriteria}
     * @return quotas
     */
    List<T> getByCriteria(QuotaSearchCriteria searchCriteria);

    /**
     * Gets count by search criteria.
     *
     * @param searchCriteria {@link QuotaSearchCriteria}
     * @return count of quotas
     */
    Integer countByCriteria(QuotaSearchCriteria searchCriteria);

    /**
     * Gets work period from interval.
     *
     * @param startDate start date
     * @param endDate end date
     * @param doctorId doctor id
     * @param workplaceId workplace id
     * @return quota period if exist
     */
    Quota getWorkPeriod(Date startDate, Date endDate, String doctorId, String workplaceId);

}
