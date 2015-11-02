package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

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
}
