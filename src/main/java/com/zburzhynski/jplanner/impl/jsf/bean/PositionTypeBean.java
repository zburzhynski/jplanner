package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.PositionType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Position type bean.
 * <p/>
 * Date: 06.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class PositionTypeBean implements Serializable {

    /**
     * Position type values.
     *
     * @return position type values
     */
    public PositionType[] getPositionTypes() {
        return PositionType.values();
    }

}
