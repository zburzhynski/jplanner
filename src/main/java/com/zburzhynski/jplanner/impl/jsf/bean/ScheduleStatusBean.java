package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.ScheduleStatus;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 * Schedule status bean.
 * <p/>
 * Date: 03.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class ScheduleStatusBean implements Serializable {

    /**
     * Gets schedule statuses.
     *
     * @return schedule statuses
     */
    public ScheduleStatus[] getStatuses() {
        return ScheduleStatus.values();
    }

}
