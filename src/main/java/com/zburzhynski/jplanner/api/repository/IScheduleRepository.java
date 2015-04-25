package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

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

}