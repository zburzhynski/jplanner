package com.zburzhynski.jplanner.api.exception;

/**
 * Jplanner base exception.
 * <p/>
 * Date: 30.11.2015
 *
 * @author Vladimir Zburzhynski
 */
public class JplannerException extends Exception {

    /**
     * Default constructor.
     */
    public JplannerException() {
    }

    /**
     * Constructor.
     *
     * @param message message
     */
    public JplannerException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message message
     * @param cause   cause
     */
    public JplannerException(String message, Throwable cause) {
        super(message, cause);
    }

}
