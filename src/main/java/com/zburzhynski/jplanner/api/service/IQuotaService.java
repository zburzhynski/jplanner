package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;

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
}
