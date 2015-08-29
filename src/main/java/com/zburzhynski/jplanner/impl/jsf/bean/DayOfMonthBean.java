package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.DayOfMonth;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Day of month bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class DayOfMonthBean implements Serializable {

    /**
     * Gets day of month.
     *
     * @return day of month
     */
     public DayOfMonth[] getDays() {
         return DayOfMonth.values();
     }

}
