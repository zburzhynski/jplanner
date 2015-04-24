package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;

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

}
