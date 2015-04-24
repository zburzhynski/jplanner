package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Schedule event repository interface.
 * <p/>
 * Date: 23.04.15
 *
 * @author Vladimir Zburzhynski
 */
public interface IScheduleRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

}
