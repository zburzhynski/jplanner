package com.zburzhynski.jplanner.impl.rest.exception;

/**
 * Employee not found exception.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
public class EmployeeNotFoundException extends Exception {

    /**
     * Default constructor.
     */
    public EmployeeNotFoundException() {
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
