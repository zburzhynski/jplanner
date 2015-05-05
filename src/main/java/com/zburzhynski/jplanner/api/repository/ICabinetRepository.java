package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

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
     * Checks is cabinet used.
     *
     * @param cabinet to check
     * @return true if used, else false
     */
    boolean isUsed(T cabinet);
}