package com.zburzhynski.jplanner.impl.jsf.bean;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.converter.ScheduleConverter;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class ScheduleBean implements Serializable {

    private static final int MIN_EVENT_LENGTH = 30;

    private ScheduleModel eventModel = new DefaultScheduleModel();

    private ScheduleEvent event = new DefaultScheduleEvent();

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        List<Schedule> events = scheduleService.getAll();
        for (Schedule schedule : events) {
            ScheduleEvent scheduleEvent = ScheduleConverter.convert(schedule);
            eventModel.getEvents().add(scheduleEvent);
        }
    }

    /**
     * Creates schedule event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void createEvent(SelectEvent selectEvent) {
        event = buildScheduleEvent(selectEvent);
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
     * Moves schedule event.
     *
     * @param moveEvent {@link ScheduleEntryMoveEvent}
     */
    public void moveEvent(ScheduleEntryMoveEvent moveEvent) {
        ScheduleEvent scheduleEvent = moveEvent.getScheduleEvent();
        Schedule schedule = ScheduleConverter.convert(scheduleEvent);
        scheduleService.saveOrUpdate(schedule);
    }

    /**
     * Resize schedule event.
     *
     * @param resizeEvent {@link ScheduleEntryResizeEvent}
     */
    public void resizeEvent(ScheduleEntryResizeEvent resizeEvent) {
        ScheduleEvent scheduleEvent = resizeEvent.getScheduleEvent();
        Schedule schedule = ScheduleConverter.convert(scheduleEvent);
        scheduleService.saveOrUpdate(schedule);
    }

    /**
     * Saves schedule event.
     *
     * @return path for navigating
     */
    public String saveEvent() {
        if (isBlank(event.getId())) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        Schedule schedule = ScheduleConverter.convert(event);
        scheduleService.saveOrUpdate(schedule);
        init();
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

    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private ScheduleEvent buildScheduleEvent(SelectEvent selectEvent) {
        Date startDate = (Date) selectEvent.getObject();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MINUTE, MIN_EVENT_LENGTH);
        Date endDate = calendar.getTime();
        ScheduleEvent scheduleEvent = new DefaultScheduleEvent(EMPTY, startDate, endDate);
        return scheduleEvent;
    }

}
