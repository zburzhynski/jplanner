package com.zburzhynski.jplanner.impl.rest.exception;

/**
 * Schedule event already exist exception.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
public class ScheduleEventAlreadyExistException extends Exception {

    /**
     * Default constructor.
     */
    public ScheduleEventAlreadyExistException() {
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public ScheduleEventAlreadyExistException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public ScheduleEventAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
