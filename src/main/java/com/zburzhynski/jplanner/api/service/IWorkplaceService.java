package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;

import java.util.List;

/**
 * Workplace service interface.
 * <p/>
 * Date: 03.08.2015
 *
 * @param <T> The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IWorkplaceService<T extends IDomain> {

    /**
     * Gets all workplaces.
     *
     * @return workplaces
     */
    List<T> getAll();

}
