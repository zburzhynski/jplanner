package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.CabinetSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Cabinet service.
 * <p/>
 * Date: 05.05.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Mikalai Karabeika
 */
public interface ICabinetService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets cabinets by criteria.
     *
     * @param searchCriteria {@link CabinetSearchCriteria} cabinet search criteria
     * @return cabinets
     */
    List<T> getByCriteria(CabinetSearchCriteria searchCriteria);

    /**
     * Counts cabinets by criteria.
     *
     * @param searchCriteria {@link CabinetSearchCriteria} cabinet search criteria
     * @return cabinets count
     */
    int countByCriteria(CabinetSearchCriteria searchCriteria);

    /**
     * Checks is cabinet used anywhere.
     *
     * @param cabinet to check
     * @return true if used, else false
     */
    boolean isUsed(T cabinet);
}