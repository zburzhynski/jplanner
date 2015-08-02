package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.ScheduleViewType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Schedule view type bean.
 * <p/>
 * Date: 01.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class ScheduleViewTypeBean implements Serializable {

    /**
     * Gets schedule view types.
     *
     * @return schedule view types
     */
    public ScheduleViewType[] getViewTypes() {
        return ScheduleViewType.values();
    }

}
