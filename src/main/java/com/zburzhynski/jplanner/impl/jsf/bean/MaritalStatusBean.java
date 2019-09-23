package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.MaritalStatus;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Social status values.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class MaritalStatusBean implements Serializable {

    /**
     * Gets marital status values.
     *
     * @return marital status values
     */
    public MaritalStatus[] getMaritalStatuses() {
        return MaritalStatus.values();
    }

}
