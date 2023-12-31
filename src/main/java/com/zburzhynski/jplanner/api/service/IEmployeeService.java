package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.criteria.AvailableEmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.EmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.exception.LinkedAvailableResourceExistException;
import com.zburzhynski.jplanner.impl.domain.Employee;

import java.util.List;

/**
 * Employee service.
 * <p/>
 * Date: 02.05.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IEmployeeService<ID, T extends IDomain> {

    /**
     * Gets employee by id.
     *
     * @param id unique identifier of employee
     * @return object
     */
    T getById(ID id);

    /**
     * Saves or updates employee.
     *
     * @param employee employee to save or update
     * @return true if success, false otherwise
     */
    boolean saveOrUpdate(T employee);

    /**
     * Deletes employee.
     *
     * @param resource employee to delete
     * @throws LinkedAvailableResourceExistException if employee has linked available resource
     */
    void delete(T resource) throws LinkedAvailableResourceExistException;

    /**
     * Gets employees by criteria.
     *
     * @param searchCriteria {@link EmployeeSearchCriteria} employee search criteria
     * @return employees
     */
    List<T> getByCriteria(EmployeeSearchCriteria searchCriteria);

    /**
     * Counts employees by criteria.
     *
     * @param searchCriteria {@link EmployeeSearchCriteria} employee search criteria
     * @return employees count
     */
    int countByCriteria(EmployeeSearchCriteria searchCriteria);

    /**
     * Gets all employees.
     *
     * @return employees
     */
    List<T> getAll();

    /**
     * Replicates employee.
     *
     * @param employee employee to replicate
     */
    void replicate(T employee);

    /**
     * Gets employee by login.
     *
     * @param login user login
     * @return employee
     */
    T getByLogin(String login);

    /**
     * Gets employees by job position.
     *
     * @param positionType {@link PositionType}
     * @return employees
     */
    List<T> getByPosition(PositionType positionType);

    /**
     * Gets available employees by criteria.
     *
     * @param searchCriteria {@link AvailableEmployeeSearchCriteria} available employee search criteria
     * @return employees
     */
    List<Employee> getAvailable(AvailableEmployeeSearchCriteria searchCriteria);

    /**
     * Checks is employee used anywhere.
     *
     * @param employee employee
     * @return true if used, else false
     */
    boolean isUsed(T employee);

}
