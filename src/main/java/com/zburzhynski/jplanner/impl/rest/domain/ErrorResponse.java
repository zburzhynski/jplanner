package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Error response.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class ErrorResponse implements Serializable {

    private String errorId;

    private String errorMessage;

    /**
     * Default constructor.
     */
    public ErrorResponse() {
    }

    /**
     * Constructor.
     *
     * @param errorId      error id
     * @param errorMessage error message
     */
    public ErrorResponse(String errorId, String errorMessage) {
        this.errorId = errorId;
        this.errorMessage = errorMessage;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
