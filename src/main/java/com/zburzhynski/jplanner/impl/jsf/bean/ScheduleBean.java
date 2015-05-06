package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.COLON;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.NEWLINE;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENTS;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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

    private static final String SCHEDULER_COMPONENT = "eventsForm:scheduler";

    private static final String PATIENT = "schedule.patient";

    private static final String DOCTOR = "schedule.doctor";

    private static final String COMPLAINT = "schedule.complaint";

    private ScheduleModel eventModel;

    private Cabinet cabinet;

    private Workplace workplace;

    private Schedule event;

    private String scheduleView = "agendaWeek";

    private Date initialDate = new Date();

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        List<Cabinet> cabinets = cabinetService.getAll();
        if (isNotEmpty(cabinets)) {
            cabinet = (Cabinet) cabinetService.getById(cabinets.get(0).getId());
            if (isNotEmpty(cabinet.getWorkplaces())) {
                workplace = cabinet.getWorkplaces().get(0);
            }
        }
        eventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                ScheduleSearchCriteria searchCriteria = buildScheduleSearchCriteria(start, end);
                List<Schedule> events = scheduleService.getByCriteria(searchCriteria);
                eventModel.getEvents().addAll(events);
            }
        };
    }

    /**
     * Creates schedule event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void createEvent(SelectEvent selectEvent) {
        initialDate = (Date) selectEvent.getObject();
        event = buildScheduleEvent(selectEvent);
        JsfUtils.redirect(SCHEDULE_EVENT.getPath());
    }

    /**
     * Edits schedule event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void editEvent(SelectEvent selectEvent) {
        ScheduleEvent scheduleEvent = (ScheduleEvent) selectEvent.getObject();
        initialDate = scheduleEvent.getStartDate();
        event = (Schedule) scheduleService.getById(scheduleEvent.getId());
        JsfUtils.redirect(SCHEDULE_EVENT.getPath());
    }

    /**
     * Moves schedule event.
     *
     * @param moveEvent {@link ScheduleEntryMoveEvent}
     */
    public void moveEvent(ScheduleEntryMoveEvent moveEvent) {
        ScheduleEvent scheduleEvent = moveEvent.getScheduleEvent();
        Schedule schedule = (Schedule) scheduleService.getById(scheduleEvent.getId());
        schedule.setStartDate(scheduleEvent.getStartDate());
        schedule.setEndDate(scheduleEvent.getEndDate());
        scheduleService.saveOrUpdate(schedule);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:"
            + moveEvent.getDayDelta() + ", Minute delta:" + moveEvent.getMinuteDelta());
        addMessage(message);
    }

    /**
     * Resize schedule event.
     *
     * @param resizeEvent {@link ScheduleEntryResizeEvent}
     */
    public void resizeEvent(ScheduleEntryResizeEvent resizeEvent) {
        ScheduleEvent scheduleEvent = resizeEvent.getScheduleEvent();
        Schedule schedule = (Schedule) scheduleService.getById(scheduleEvent.getId());
        schedule.setEndDate(scheduleEvent.getEndDate());
        scheduleService.saveOrUpdate(schedule);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:"
            + resizeEvent.getDayDelta() + ", Minute delta:" + resizeEvent.getMinuteDelta());
        addMessage(message);
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
        prepareScheduleTitle(event);
        scheduleService.saveOrUpdate(event);
        return SCHEDULE_EVENTS.getPath();
    }

    /**
     * Cancel updates schedule event.
     *
     * @return path for navigating
     */
    public String cancelUpdateEvent() {
        return SCHEDULE_EVENTS.getPath();
    }

    /**
     * Select cabinet listener.
     */
    public void selectCabinetListener() {
        cabinet = (Cabinet) cabinetService.getById(cabinet.getId());
        workplace = isNotEmpty(cabinet.getWorkplaces()) ? cabinet.getWorkplaces().get(0) : null;
        JsfUtils.update(SCHEDULER_COMPONENT);
    }

    /**
     * Select workplace listener.
     */
    public void selectWorkplaceListener() {
        JsfUtils.update(SCHEDULER_COMPONENT);
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

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public Schedule getEvent() {
        return event;
    }

    public void setEvent(Schedule event) {
        this.event = event;
    }

    public String getScheduleView() {
        return scheduleView;
    }

    public void setScheduleView(String scheduleView) {
        this.scheduleView = scheduleView;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    private void prepareScheduleTitle(Schedule schedule) {
        StringBuilder builder = new StringBuilder();
        builder.append(propertyReader.readProperty(PATIENT));
        builder.append(COLON + SPACE);
        builder.append(schedule.getPerson().getFullName());
        builder.append(NEWLINE);
        builder.append(propertyReader.readProperty(DOCTOR));
        builder.append(COLON + SPACE);
        builder.append(schedule.getDoctor().getPerson().getFullName());
        if (StringUtils.isNotBlank(schedule.getComplaint())) {
            builder.append(NEWLINE);
            builder.append(propertyReader.readProperty(COMPLAINT));
            builder.append(COLON + SPACE);
            builder.append(schedule.getComplaint());
        }
        schedule.setTitle(builder.toString());
    }

    private Schedule buildScheduleEvent(SelectEvent selectEvent) {
        Date startDate = (Date) selectEvent.getObject();
        Date endDate = DateUtils.addMinuteToDate(startDate, MIN_EVENT_LENGTH);
        Schedule scheduleEvent = new Schedule(startDate, endDate, EMPTY);
        scheduleEvent.setWorkplace(workplace);
        return scheduleEvent;
    }

    private ScheduleSearchCriteria buildScheduleSearchCriteria(Date start, Date end) {
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        searchCriteria.setWorkplace(workplace);
        Date startDate = DateUtils.setInitialTime(DateUtils.addDayToDate(start, 1));
        Date endDate = DateUtils.setFinalTime(end);
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
        return searchCriteria;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
