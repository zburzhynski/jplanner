package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.DayOfWeek;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Day of week bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class DayOfWeekBean implements Serializable {

    /**
     * Gets day of weeks.
     *
     * @return day of weeks
     */
    public DayOfWeek[] getDays() {
        return DayOfWeek.values();
    }

}
