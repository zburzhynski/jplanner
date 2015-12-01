package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.exception.LinkedTimetablesExistException;

import java.util.List;

/**
 * Available resource service.
 * <p/>
 * Date: 17.10.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IAvailableResourceService<ID, T extends IDomain> {

    /**
     * Gets available resource by id.
     *
     * @param id unique identifier of available resource
     * @return object
     */
    T getById(ID id);

    /**
     * Saves or updates available resource.
     *
     * @param resource available resource to save or update
     * @return true if success, false otherwise
     */
    boolean saveOrUpdate(T resource);

    /**
     * Deletes available resource.
     *
     * @param resource available resource to delete
     * @throws LinkedTimetablesExistException if available resource has linked timetables
     */
    void delete(T resource) throws LinkedTimetablesExistException;

    /**
     * Gets available resources by criteria.
     *
     * @param searchCriteria {@link AvailableResourceSearchCriteria} available resource search criteria
     * @return available resources
     */
    List<T> getByCriteria(AvailableResourceSearchCriteria searchCriteria);

    /**
     * Counts available resources by criteria.
     *
     * @param searchCriteria {@link AvailableResourceSearchCriteria} available resource search criteria
     * @return available resources count
     */
    int countByCriteria(AvailableResourceSearchCriteria searchCriteria);

    /**
     * Gets all available resources.
     *
     * @return available resources
     */
    List<T> getAll();

}
