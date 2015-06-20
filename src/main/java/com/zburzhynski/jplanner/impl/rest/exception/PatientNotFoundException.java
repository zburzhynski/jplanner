package com.zburzhynski.jplanner.impl.rest.exception;

/**
 * Patient not found exception.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
public class PatientNotFoundException extends Exception {

    /**
     * Default constructor.
     */
    public PatientNotFoundException() {
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public PatientNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
