package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.TimetableStatus;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Timetable status list bean.
 * <p/>
 * Date: 16.11.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@ViewScoped
public class TimetableStatusListBean implements Serializable {

    /**
     * Gets timetable statuses.
     *
     * @return timetable statuses
     */
    public TimetableStatus[] getStatuses() {
        return TimetableStatus.values();
    }
}
