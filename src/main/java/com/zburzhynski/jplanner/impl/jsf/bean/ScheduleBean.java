package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.View;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.util.Date;
import java.util.TimeZone;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Schedule event bean.
 * <p/>
 * Date: 21.04.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class ScheduleBean {

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private ScheduleEvent event = new DefaultScheduleEvent();

    /**
     * Creates schedule event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void createEvent(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        FacesContext fc = FacesContext.getCurrentInstance();
        NavigationHandler nav = fc.getApplication().getNavigationHandler();
        nav.handleNavigation(fc, null, View.SCHEDULE_EVENT.getPath());
        fc.renderResponse();
    }

    /**
     * Edits schedule event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void editEvent(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        FacesContext fc = FacesContext.getCurrentInstance();
        NavigationHandler nav = fc.getApplication().getNavigationHandler();
        nav.handleNavigation(fc, null, View.SCHEDULE_EVENT.getPath());
        fc.renderResponse();
    }

    /**
     * Saves schedule event.
     *
     * @return path for navigating
     */
    public String saveEvent() {
        if (StringUtils.isBlank(event.getId())) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        return View.SCHEDULE_EVENTS.getPath();
    }

    /**
     * Cancel updates schedule event.
     *
     * @return path for navigating
     */
    public String cancelUpdateEvent() {
        return View.SCHEDULE_EVENTS.getPath();
    }

    public String getTimeZone() {
        return TimeZone.getDefault().getID();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

}
