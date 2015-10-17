package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Available resource repository interface.
 * <p/>
 * Date: 17.10.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IAvailableResourceRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds available resources by criteria.
     *
     * @param searchCriteria {@link AvailableResourceSearchCriteria} available resource search criteria
     * @return available resources
     */
    List<T> findByCriteria(AvailableResourceSearchCriteria searchCriteria);

    /**
     * Counts available resources by criteria.
     *
     * @param searchCriteria {@link AvailableResourceSearchCriteria} available resource search criteria
     * @return available resources count
     */
    int countByCriteria(AvailableResourceSearchCriteria searchCriteria);

    /**
     * Checks is available resource used.
     *
     * @param resource available resource to check
     * @return true if used, else false
     */
    boolean isUsed(T resource);

}
