package com.zburzhynski.jplanner.impl.rest.domain;

/**
 * Dental visit.
 * <p/>
 * Date: 17.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public class VisitDto {

    private String id;

    /**
     * Default constructor.
     */
    public VisitDto() {
    }

    /**
     * Constructor.
     *
     * @param id dental visit id
     */
    public VisitDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
