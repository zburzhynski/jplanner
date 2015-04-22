package com.zburzhynski.jplanner.impl.jsf.bean;

import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Schedule bean.
 * <p/>
 * Date: 21.04.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class ScheduleBean {

    private ScheduleModel eventModel = new DefaultScheduleModel();

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

}
