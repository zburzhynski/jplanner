package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.PositionSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.exception.LinkedEmployeeExistException;

import java.util.List;

/**
 * Job position service.
 * <p/>
 * Date: 04.05.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IPositionService<ID, T extends IDomain> {

    /**
     * Gets job position by id.
     *
     * @param id unique identifier of job position
     * @return object
     */
    T getById(ID id);

    /**
     * Saves or updates job position.
     *
     * @param resource job position to save or update
     * @return true if success, false otherwise
     */
    boolean saveOrUpdate(T resource);

    /**
     * Deletes job position.
     *
     * @param position position to delete
     * @throws LinkedEmployeeExistException if position has linked employee
     */
    void delete(T position) throws LinkedEmployeeExistException;

    /**
     * Gets all job positions.
     *
     * @return job positions
     */
    List<T> getAll();

    /**
     * Replicates position.
     *
     * @param position position to replicate
     */
    void replicate(T position);

    /**
     * Gets positions by criteria.
     *
     * @param criteria {@link PositionSearchCriteria} position search criteria
     * @return positions
     */
    List<T> getByCriteria(PositionSearchCriteria criteria);

    /**
     * Counts positions by criteria.
     *
     * @param searchCriteria {@link PositionSearchCriteria} position search criteria
     * @return positions count
     */
    int countByCriteria(PositionSearchCriteria searchCriteria);

    /**
     * Checks is position used anywhere.
     *
     * @param position position
     * @return true if used, else false
     */
    boolean isUsed(T position);

}
