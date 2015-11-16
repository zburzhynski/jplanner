package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.domain.QuotaType;

import java.util.Date;
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
     * Finds intersecting of quotas.
     *
     * @param startDate start date
     * @param endDate end date
     * @param types quota types
     * @return intersecting of quotas
     */
    List<T> findIntersecting(Date startDate, Date endDate, List<QuotaType> types);

}
