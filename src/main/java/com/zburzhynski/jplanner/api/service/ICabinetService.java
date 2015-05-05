package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Class has common methods for working with jsf beans.
 * <p/>
 * Date: 05.05.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Mikalai Karabeika
 */
public interface ICabinetService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Checks is cabinet used anywhere.
     *
     * @param cabinet to check
     * @return true if used, else false
     */
    boolean isUsed(T cabinet);
}