package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.domain.PositionType;

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
public interface IEmployeeService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets employees by job position.
     *
     * @param positionType {@link PositionType}
     * @return employees
     */
    List<T> getByPosition(PositionType positionType);

    /**
     * Gets employee by login.
     *
     * @param login user login
     * @return employee
     */
    T getByLogin(String login);

    /**
     * Checks is employee used anywhere.
     *
     * @param employee employee
     * @return true if used, else false
     */
    boolean isUsed(T employee);

}
