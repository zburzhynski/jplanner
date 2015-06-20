package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.AMPERSAND;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.COLON;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.NEWLINE;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.QUESTION_MARK;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENTS;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.api.domain.ScheduleStatus;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.jsf.validator.ScheduleValidator;
import com.zburzhynski.jplanner.impl.rest.client.IPatientRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.PatientNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleEventAlreadyExistException;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.MessageHelper;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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

    private static final String WORKPLACE_NOT_SELECTED = "schedule.workplaceNotSelected";

    private static final String EVENT_MOVED = "schedule.eventMoved";

    private static final String EVENT_RESIZED = "schedule.eventResized";

    private static final String PATIENT = "schedule.patient";

    private static final String DOCTOR = "schedule.doctor";

    private static final String COMPLAINT = "schedule.complaint";

    private static final String START_DENTAL_VISIT_URL = "pages/integration/visit.xhtml";

    private static final String GO_TO_CARD_URL = "pages/integration/card.xhtml";

    private static final String SCHEDULE_ID_PARAM = "scheduleId=";

    private static final String PATIENT_ID_PARAM = "patientId=";

    private ScheduleModel eventModel;

    private Cabinet cabinet;

    private Workplace workplace;

    private Schedule event;

    private String scheduleView = "agendaWeek";

    private Date initialDate = new Date();

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{scheduleValidator}")
    private ScheduleValidator scheduleValidator;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

    @ManagedProperty(value = "#{messageHelper}")
    private MessageHelper messageHelper;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

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
        if (cabinet != null && CollectionUtils.isNotEmpty(cabinet.getWorkplaces())) {
            initialDate = (Date) selectEvent.getObject();
            event = buildScheduleEvent(selectEvent);
            JsfUtils.redirect(SCHEDULE_EVENT.getPath());
        } else {
            messageHelper.addMessage(WORKPLACE_NOT_SELECTED);
        }
    }

    /**
     * Selects schedule event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void selectEvent(SelectEvent selectEvent) {
        ScheduleEvent scheduleEvent = (ScheduleEvent) selectEvent.getObject();
        initialDate = scheduleEvent.getStartDate();
        event = (Schedule) scheduleService.getById(scheduleEvent.getId());
    }

    /**
     * Moves schedule event.
     *
     * @param moveEvent {@link ScheduleEntryMoveEvent}
     */
    public void moveEvent(ScheduleEntryMoveEvent moveEvent) {
        ScheduleEvent scheduleEvent = moveEvent.getScheduleEvent();
        initialDate = scheduleEvent.getStartDate();
        Schedule schedule = (Schedule) scheduleService.getById(scheduleEvent.getId());
        Date originalStartDate = schedule.getStartDate();
        Date originalEndDate = schedule.getEndDate();
        schedule.setStartDate(scheduleEvent.getStartDate());
        schedule.setEndDate(scheduleEvent.getEndDate());
        boolean valid = scheduleValidator.validateAvailability(schedule);
        if (!valid) {
            schedule.setStartDate(originalStartDate);
            schedule.setEndDate(originalEndDate);
            eventModel.updateEvent(schedule);
            return;
        }
        scheduleService.saveOrUpdate(schedule);
        messageHelper.addMessage(EVENT_MOVED, schedule.getTitle());
    }

    /**
     * Resize schedule event.
     *
     * @param resizeEvent {@link ScheduleEntryResizeEvent}
     */
    public void resizeEvent(ScheduleEntryResizeEvent resizeEvent) {
        ScheduleEvent scheduleEvent = resizeEvent.getScheduleEvent();
        initialDate = scheduleEvent.getStartDate();
        Schedule schedule = (Schedule) scheduleService.getById(scheduleEvent.getId());
        Date originalEndDate = schedule.getEndDate();
        schedule.setEndDate(scheduleEvent.getEndDate());
        boolean valid = scheduleValidator.validateAvailability(schedule);
        if (!valid) {
            schedule.setEndDate(originalEndDate);
            eventModel.updateEvent(schedule);
            return;
        }
        scheduleService.saveOrUpdate(schedule);
        messageHelper.addMessage(EVENT_RESIZED, schedule.getTitle());
    }

    /**
     * Saves schedule event.
     *
     * @return path for navigating
     */
    public String saveEvent() {
        boolean valid = scheduleValidator.validate(event);
        if (!valid) {
            return null;
        }
        prepareScheduleTitle(event);
        saveModel();
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
     * Starts schedule event.
     */
    //TODO: error codes move to enum
    public void startEvent() {
        if (configBean.isJdentIntegrationEnabled()) {
            CreateVisitRequest request = new CreateVisitRequest();
            request.setScheduleId(event.getId());
            request.getPatient().setId(event.getPatientId());
            request.getPatient().setSurname(event.getPerson().getSurname());
            request.getPatient().setName(event.getPerson().getName());
            request.getPatient().setPatronymic(event.getPerson().getPatronymic());
            request.getPatient().setBirthday(event.getPerson().getBirthday());
            request.getPatient().setGender(event.getPerson().getGender().name());
            request.setDoctorId(event.getDoctor().getId());
            request.setVisitDate(event.getStartDate());
            request.setComplaint(event.getComplaint());
            try {
                CreateVisitResponse response = patientRestClient.createVisit(request);
                if (response != null) {
                    event.setPatientId(response.getPatientId());
                    event.setStatus(ScheduleStatus.STARTED);
                    saveModel();
                    String url = configBean.getJdentUrl() + START_DENTAL_VISIT_URL + QUESTION_MARK
                        + SCHEDULE_ID_PARAM + event.getId() + AMPERSAND + PATIENT_ID_PARAM + event.getPatientId();
                    JsfUtils.externalRedirect(url);
                }
            } catch (PatientNotFoundException e) {
                messageHelper.addMessage("error.patientNotFound");
            } catch (ScheduleEventAlreadyExistException e) {
                messageHelper.addMessage("error.scheduleEventAlreadyExist");
            } catch (EmployeeNotFoundException e) {
                messageHelper.addMessage("error.employeeNotFound");
            }
        } else {
            event.setStatus(ScheduleStatus.STARTED);
            saveModel();
            JsfUtils.update(SCHEDULER_COMPONENT);
        }
    }

    /**
     * Finish schedule event.
     *
     * @return path for navigating
     */
    public String finishEvent() {
        event.setStatus(ScheduleStatus.FINISHED);
        saveModel();
        return SCHEDULE_EVENTS.getPath();
    }

    /**
     * Cancels schedule event.
     *
     * @return path for navigating
     */
    public String cancelEvent() {
        event.setStatus(ScheduleStatus.CANCELED);
        saveModel();
        return SCHEDULE_EVENTS.getPath();
    }

    /**
     * Go to patient card.
     */
    public void goToCard() {
        String url = configBean.getJdentUrl() + GO_TO_CARD_URL + QUESTION_MARK
            + PATIENT_ID_PARAM + event.getPatientId();
        JsfUtils.externalRedirect(url);
    }

    /**
     * Edits schedule event.
     *
     * @return path for navigating
     */
    public String editEvent() {
        return SCHEDULE_EVENT.getPath();
    }

    /**
     * Removes schedule event.
     *
     * @return path for navigating
     */
    public String removeEvent() {
        scheduleService.delete(event);
        return SCHEDULE_EVENTS.getPath();
    }

    /**
     * Choose patient.
     *
     * @return path for navigating
     */
    public String choosePatient() {
        return View.PATIENTS.getPath();
    }

    /**
     * Clear patient.
     */
    public void clearPatient() {
        event.setPatientId(null);
        event.getPerson().setGender(Gender.M);
        event.getPerson().setBirthday(new Date());
        event.getPerson().setSurname(null);
        event.getPerson().setName(null);
        event.getPerson().setPatronymic(null);
    }

    /**
     * Select view listener.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void selectViewListener(SelectEvent selectEvent) {
        scheduleView = (String) selectEvent.getObject();
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

    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    public void setScheduleValidator(ScheduleValidator scheduleValidator) {
        this.scheduleValidator = scheduleValidator;
    }

    public void setPatientRestClient(IPatientRestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    private void saveModel() {
        if (isBlank(event.getId())) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        scheduleService.saveOrUpdate(event);
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

}
