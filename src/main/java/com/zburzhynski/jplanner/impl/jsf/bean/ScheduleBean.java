package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.AMPERSAND;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.COLON;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.NEWLINE;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.QUESTION_MARK;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jplanner.api.domain.Error.EMPLOYEE_NOT_FOUND_EXCEPTION;
import static com.zburzhynski.jplanner.api.domain.Error.JDENT_UNAVAILABLE_EXCEPTION;
import static com.zburzhynski.jplanner.api.domain.Error.PATIENT_NOT_FOUND_EXCEPTION;
import static com.zburzhynski.jplanner.api.domain.Error.SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENTS;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.api.domain.ScheduleStatus;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.jsf.validator.ScheduleValidator;
import com.zburzhynski.jplanner.impl.rest.client.IVisitRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchVisitResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.JdentUnavailableException;
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

    private static final int FIRTH_HOUR = 8;

    private static final String SCHEDULER_COMPONENT = "eventsForm:scheduler";

    private static final String WORKPLACE_NOT_SELECTED = "schedule.workplaceNotSelected";

    private static final String EVENT_MOVED = "schedule.eventMoved";

    private static final String EVENT_RESIZED = "schedule.eventResized";

    private static final String PATIENT = "schedule.patient";

    private static final String DOCTOR = "schedule.doctor";

    private static final String REASON = "schedule.reason";

    private static final String START_DENTAL_VISIT_URL = "pages/integration/visit.xhtml";

    private static final String GO_TO_CARD_URL = "pages/integration/card.xhtml";

    private static final String SCHEDULE_ID_PARAM = "scheduleId=";

    private static final String PATIENT_ID_PARAM = "patientId=";

    private ScheduleModel eventModel;

    private Cabinet cabinet;

    private Workplace workplace;

    private Schedule event;

    private String view = "agendaWeek";

    private Date initialDate = new Date();

    private int firstHour = FIRTH_HOUR;

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{scheduleValidator}")
    private ScheduleValidator scheduleValidator;

    @ManagedProperty(value = "#{visitRestClient}")
    private IVisitRestClient visitRestClient;

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
            cabinet = cabinets.get(0);
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
            firstHour = DateUtils.extractHour(initialDate);
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
        firstHour = DateUtils.extractHour(initialDate);
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
    public void startEvent() {
        if (configBean.isJdentIntegrationEnabled()) {
            CreateVisitRequest request = new CreateVisitRequest();
            request.setScheduleId(event.getId());
            request.getPatient().setId(event.getClient().getJdentPatientId());
            request.getPatient().setSurname(event.getClient().getPerson().getSurname());
            request.getPatient().setName(event.getClient().getPerson().getName());
            request.getPatient().setPatronymic(event.getClient().getPerson().getPatronymic());
            request.getPatient().setBirthday(event.getClient().getPerson().getBirthday());
            request.getPatient().setGender(event.getClient().getPerson().getGender().name());
            request.setDoctorId(event.getDoctor().getId());
            request.setVisitDate(event.getStartDate());
            request.setComplaint(event.getClient().getReason());
            String jdentUrl = configBean.getJdentUrl();
            try {
                CreateVisitResponse response = visitRestClient.createVisit(request, jdentUrl);
                if (response != null) {
                    event.getClient().setJdentPatientId(response.getPatientId());
                    event.setStatus(ScheduleStatus.STARTED);
                    saveModel();
                    String url = jdentUrl + START_DENTAL_VISIT_URL + QUESTION_MARK + SCHEDULE_ID_PARAM
                        + event.getId() + AMPERSAND + PATIENT_ID_PARAM + event.getClient().getJdentPatientId();
                    JsfUtils.externalRedirect(url);
                }
            } catch (PatientNotFoundException e) {
                messageHelper.addMessage(PATIENT_NOT_FOUND_EXCEPTION.getMessage());
            } catch (ScheduleEventAlreadyExistException e) {
                messageHelper.addMessage(SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION.getMessage());
            } catch (EmployeeNotFoundException e) {
                messageHelper.addMessage(EMPLOYEE_NOT_FOUND_EXCEPTION.getMessage());
            } catch (JdentUnavailableException e) {
                messageHelper.addMessage(JDENT_UNAVAILABLE_EXCEPTION.getMessage());
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
            + PATIENT_ID_PARAM + event.getClient().getJdentPatientId();
        JsfUtils.externalRedirect(url);
    }

    /**
     * Edits schedule event.
     *
     * @return path for navigating
     */
    public String editEvent() {
        if (configBean.isJdentIntegrationEnabled()) {
            try {
                SearchVisitResponse response = visitRestClient.getByScheduleId(event.getId(), configBean.getJdentUrl());
                if (CollectionUtils.isNotEmpty(response.getVisits())) {
                    messageHelper.addMessage(SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION.getMessage());
                    return null;
                }
                return SCHEDULE_EVENT.getPath();
            } catch (JdentUnavailableException e) {
                messageHelper.addMessage(JDENT_UNAVAILABLE_EXCEPTION.getMessage());
                return null;
            }
        } else {
            return SCHEDULE_EVENT.getPath();
        }
    }

    /**
     * Removes schedule event.
     *
     * @return path for navigating
     */
    public String removeEvent() {
        if (configBean.isJdentIntegrationEnabled()) {
            try {
                SearchVisitResponse response = visitRestClient.getByScheduleId(event.getId(), configBean.getJdentUrl());
                if (CollectionUtils.isNotEmpty(response.getVisits())) {
                    messageHelper.addMessage(SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION.getMessage());
                    return null;
                }
                scheduleService.delete(event);
                return SCHEDULE_EVENTS.getPath();
            } catch (JdentUnavailableException e) {
                messageHelper.addMessage(JDENT_UNAVAILABLE_EXCEPTION.getMessage());
                return null;
            }
        } else {
            scheduleService.delete(event);
            return SCHEDULE_EVENTS.getPath();
        }
    }

    /**
     * Choose patient.
     *
     * @return path for navigating
     */
    public String choosePatient() {
        PatientBean patientBean = JsfUtils.getBean("patientBean");
        if (patientBean != null) {
            patientBean.setSearchPatientRequest(new SearchPatientRequest());
            patientBean.setPatient(null);
            patientBean.init();
        }
        return View.PATIENTS.getPath();
    }

    /**
     * Clear patient.
     */
    public void clearPatient() {
        event.getClient().setJdentPatientId(null);
        event.getClient().getPerson().setGender(Gender.M);
        event.getClient().getPerson().setBirthday(new Date());
        event.getClient().getPerson().setSurname(null);
        event.getClient().getPerson().setName(null);
        event.getClient().getPerson().setPatronymic(null);
    }

    /**
     * Select view listener.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void selectViewListener(SelectEvent selectEvent) {
        view = (String) selectEvent.getObject();
    }

    /**
     * Select cabinet listener.
     */
    public void selectCabinetListener() {
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

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public int getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(int firstHour) {
        this.firstHour = firstHour;
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

    public void setVisitRestClient(IVisitRestClient visitRestClient) {
        this.visitRestClient = visitRestClient;
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
        builder.append(schedule.getClient().getPerson().getFullName());
        builder.append(NEWLINE);
        builder.append(propertyReader.readProperty(DOCTOR));
        builder.append(COLON + SPACE);
        builder.append(schedule.getDoctor().getPerson().getFullName());
        if (StringUtils.isNotBlank(schedule.getClient().getReason())) {
            builder.append(NEWLINE);
            builder.append(propertyReader.readProperty(REASON));
            builder.append(COLON + SPACE);
            builder.append(schedule.getClient().getReason());
        }
        schedule.setTitle(builder.toString());
    }

    private Schedule buildScheduleEvent(SelectEvent selectEvent) {
        Date startDate = (Date) selectEvent.getObject();
        Date endDate = DateUtils.addMinuteToDate(startDate, configBean.getEventDuration());
        Schedule scheduleEvent = new Schedule(startDate, endDate, EMPTY);
        scheduleEvent.setWorkplace(workplace);
        return scheduleEvent;
    }

    private ScheduleSearchCriteria buildScheduleSearchCriteria(Date start, Date end) {
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        searchCriteria.setWorkplace(workplace);
        Date startDate;
        Date endDate;
        if (TimeZone.getDefault().getRawOffset() >= 0) {
            startDate = DateUtils.setInitialTime(start);
            endDate = DateUtils.setFinalTime(DateUtils.addDayToDate(end, -1));
        } else {
            startDate = DateUtils.setInitialTime(DateUtils.addDayToDate(start, 1));
            endDate = DateUtils.setFinalTime(end);
        }
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
        return searchCriteria;
    }

}
