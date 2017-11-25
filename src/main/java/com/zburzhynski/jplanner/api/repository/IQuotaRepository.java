package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.criteria.QuotaSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Quota repository interface.
 * <p/>
 * Date: 02.11.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IQuotaRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds quotas by search criteria.
     *
     * @param searchCriteria {@link QuotaSearchCriteria}
     * @return quotas
     */
    List<T> findByCriteria(QuotaSearchCriteria searchCriteria);

    /**
     * Finds count by search criteria.
     *
     * @param searchCriteria {@link QuotaSearchCriteria}
     * @return count of quotas
     */
    Integer countByCriteria(QuotaSearchCriteria searchCriteria);
}
