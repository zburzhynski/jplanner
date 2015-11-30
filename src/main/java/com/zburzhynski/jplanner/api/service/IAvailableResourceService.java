package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.exception.AvailableResourceHasLinkedTimetablesException;

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
public interface IAvailableResourceService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Deletes available resource.
     *
     * @param resource available resource to delete
     * @throws AvailableResourceHasLinkedTimetablesException if resource has linked timetables
     */
    void delete(T resource) throws AvailableResourceHasLinkedTimetablesException;

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

}
