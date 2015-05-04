package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Job position repository interface.
 * <p/>
 * Date: 04.05.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IPositionRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Checks is position used.
     *
     * @param position position to check
     * @return true if used, else false
     */
    boolean isUsed(T position);

}
