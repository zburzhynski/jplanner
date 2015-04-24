package com.zburzhynski.jplanner.api.domain;

import java.io.Serializable;

/**
 * Domain interface.
 * <p/>
 * Date: 23.04.15
 *
 * @author Vladimir Zburzhynski
 */
public interface IDomain extends Serializable {

    /**
     * Gets unique identifier of entity.
     *
     * @return unique identifier of entity
     */
    String getId();

    /**
     * Sets unique identifier of entity.
     *
     * @param id unique identifier of entity to set
     */
    void setId(String id);

}
