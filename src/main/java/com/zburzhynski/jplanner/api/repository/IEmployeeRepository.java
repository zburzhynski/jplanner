package com.zburzhynski.jplanner.api.repository;

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
     * Finds employees by job position.
     *
     * @param positionType {@link PositionType}
     * @return employees
     */
    List<T> findByPosition(PositionType positionType);

    /**
     * Finds employee by login.
     *
     * @param login user login
     * @return employee
     */
    public T findByLogin(String login);

    /**
     * Checks is employee used.
     *
     * @param employee to check
     * @return true if used, else false
     */
    public boolean isUsed(T employee);

}
