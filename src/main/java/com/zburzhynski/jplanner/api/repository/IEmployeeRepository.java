package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.criteria.AvailableEmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.EmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.domain.PositionType;

import java.util.List;

/**
 * Employee repository interface.
 * <p/>
 * Date: 02.05.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IEmployeeRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds employee by login.
     *
     * @param login user login
     * @return employee
     */
    public T findByLogin(String login);

    /**
     * Finds employees by job position.
     *
     * @param positionType {@link PositionType}
     * @return employees
     */
    List<T> findByPosition(PositionType positionType);

    /**
     * Finds available employees by criteria.
     *
     * @param searchCriteria {@link AvailableEmployeeSearchCriteria} available employee search criteria
     * @return employees
     */
    List<T> findAvailable(AvailableEmployeeSearchCriteria searchCriteria);

    /**
     * Gets employees by criteria.
     *
     * @param searchCriteria {@link EmployeeSearchCriteria} employee search criteria
     * @return employees
     */
    List<T> findByCriteria(EmployeeSearchCriteria searchCriteria);

    /**
     * Counts employees by criteria.
     *
     * @param searchCriteria {@link EmployeeSearchCriteria} employee search criteria
     * @return employees count
     */
    int countByCriteria(EmployeeSearchCriteria searchCriteria);

    /**
     * Checks is employee used.
     *
     * @param employee to check
     * @return true if used, else false
     */
    public boolean isUsed(T employee);

}
