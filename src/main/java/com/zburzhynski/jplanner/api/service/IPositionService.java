package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Job position service.
 * <p/>
 * Date: 04.05.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IPositionService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Checks is position used anywhere.
     *
     * @param  position position
     * @return true if used, else false
     *
     */
    boolean isUsed(T position);

}
