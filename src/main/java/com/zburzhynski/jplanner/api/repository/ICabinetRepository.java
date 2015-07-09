package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.criteria.CabinetSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Cabinet repository interface.
 * <p/>
 * Date: 05.05.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Mikalai Karabeika
 */
public interface ICabinetRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds cabinets by criteria.
     *
     * @param searchCriteria {@link CabinetSearchCriteria} cabinet search criteria
     * @return cabinets
     */
    List<T> findByCriteria(CabinetSearchCriteria searchCriteria);

    /**
     * Counts cabinets by criteria.
     *
     * @param searchCriteria {@link CabinetSearchCriteria} cabinet search criteria
     * @return cabinets count
     */
    int countByCriteria(CabinetSearchCriteria searchCriteria);

    /**
     * Checks is cabinet used.
     *
     * @param cabinet to check
     * @return true if used, else false
     */
    boolean isUsed(T cabinet);
}