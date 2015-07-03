package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.impl.criteria.PositionSearchCriteria;

import java.util.List;

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
     * Finds positions by criteria.
     *
     * @param searchCriteria {@link PositionSearchCriteria} position search criteria
     * @return positions
     */
    List<T> findByCriteria(PositionSearchCriteria searchCriteria);

    /**
     * Counts positions by criteria.
     *
     * @param searchCriteria {@link PositionSearchCriteria} position search criteria
     * @return positions count
     */
    int countByCriteria(PositionSearchCriteria searchCriteria);

    /**
     * Checks is position used.
     *
     * @param position position to check
     * @return true if used, else false
     */
    boolean isUsed(T position);

}
