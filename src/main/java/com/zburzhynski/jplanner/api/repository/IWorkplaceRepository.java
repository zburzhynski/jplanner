package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Workplace repository interface.
 * <p/>
 * Date: 03.08.2015
 *
 * @param <T> The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IWorkplaceRepository<T extends IDomain> {

    /**
     * Gets all workplaces.
     *
     * @return workplaces
     */
    List<T> getAll();

}
