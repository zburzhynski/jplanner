package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.COLON;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.NEWLINE;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jplanner.api.domain.Error.JDENT_UNAVAILABLE_EXCEPTION;
import static com.zburzhynski.jplanner.api.domain.Error.SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENTS;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.domain.ScheduleStatus;
import com.zburzhynski.jplanner.api.domain.ScheduleViewType;
import com.zburzhynski.jplanner.api.domain.TimetableStatus;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.IQuotaService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.jsf.validator.ScheduleValidator;
import com.zburzhynski.jplanner.impl.rest.client.IVisitRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchVisitResponse;
import com.zburzhynski.jplanner.impl.rest.exception.JdentUnavailableException;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.MessageHelper;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static final String WORKPLACE_PARAM = "workplace";

    public static final String START_DATE_PARAM = "startDate";

    public static final String END_DATE_PARAM = "endDate";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String SCHEDULE_ID_PARAM = "scheduleId";

    private static final String PATIENT_ID_PARAM = "patientId";

    private static final String PATIENT_SURNAME_PARAM = "patientSurname";

    private static final String PATIENT_NAME_PARAM = "patientName";

    private static final String PATIENT_PATRONYMIC_PARAM = "patientPatronymic";

    private static final String PATIENT_BIRTHDAY_PARAM = "patientBirthday";

    private static final String PATIENT_GENDER_PARAM = "patientGender";

    private static final String VISIT_DATE_PARAM = "visitDate";

    private static final String VISIT_DOCTOR_ID_PARAM = "visitDoctorId";

    private static final String VISIT_COMPLAINT_PARAM = "visitComplaint";

    private static final int FIRTH_HOUR = 8;

    private static final String SCHEDULER_COMPONENT = "eventsForm:scheduler";

    private static final String EVENT_MOVED = "schedule.eventMoved";

    private static final String EVENT_RESIZED = "schedule.eventResized";

    private static final String PATIENT = "schedule.patient";

    private static final String DOCTOR = "schedule.doctor";

    private static final String REASON = "schedule.reason";

    private static final String START_DENTAL_VISIT_URL = "pages/integration/visit.xhtml";

    private static final String GO_TO_CARD_URL = "pages/integration/card.xhtml";

    private static final String WORK_TIME_STYLE_CLASS = "workTime";

    private static final String OFF_TIME_STYLE_CLASS = "offTime";

    private static final String EVENT_NOT_IN_WORK_TIME = "scheduleValidator.eventNotInWorkTime";

    private ScheduleModel eventModel;

    private Cabinet cabinet;

    private Workplace workplace;

    private Employee doctor;

    private Schedule event;

    private String view = "agendaWeek";

    private Date initialDate = new Date();

    private int firstHour = FIRTH_HOUR;

    private ScheduleViewType viewType = ScheduleViewType.EMPLOYEE;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{quotaService}")
    private IQuotaService quotaService;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

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
        if (ScheduleViewType.WORKPLACE.equals(viewType)) {
            List<Cabinet> cabinets = cabinetService.getAll();
            if (isNotEmpty(cabinets)) {
                cabinet = cabinets.get(0);
                if (isNotEmpty(cabinet.getWorkplaces())) {
                    workplace = cabinet.getWorkplaces().get(0);
                }
            }
        } else if (ScheduleViewType.EMPLOYEE.equals(viewType)) {
            List<Employee> doctors = employeeService.getByPosition(PositionType.DOCTOR);
            if (isNotEmpty(doctors)) {
                doctor = doctors.get(0);
            }
        }
        eventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                //TODO: use checkQuotas setting
                //fillQuotas();
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
        firstHour = DateUtils.extractHour(initialDate);
        event = buildScheduleEvent(selectEvent);
        if (event == null) {
            messageHelper.addMessage(EVENT_NOT_IN_WORK_TIME);
            return;
        }
        setEmployeeListBeanParams();
        JsfUtils.redirect(SCHEDULE_EVENT.getPath());
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
        if (event != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('menu').show();");
        }
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
            String jdentUrl = configBean.getJdentUrl();
            Map<String, Object> params = new HashMap<>();
            addParam(params, SCHEDULE_ID_PARAM, event.getId());
            addParam(params, PATIENT_ID_PARAM, event.getClient().getJdentPatientId());
            addParam(params, PATIENT_SURNAME_PARAM, event.getClient().getPerson().getSurname());
            addParam(params, PATIENT_NAME_PARAM, event.getClient().getPerson().getName());
            addParam(params, PATIENT_PATRONYMIC_PARAM, event.getClient().getPerson().getPatronymic());
            addParam(params, PATIENT_BIRTHDAY_PARAM, DateUtils.formatDate(
                event.getClient().getPerson().getBirthday(), DATE_FORMAT));
            addParam(params, PATIENT_GENDER_PARAM, event.getClient().getPerson().getGender().name());
            addParam(params, VISIT_DATE_PARAM, DateUtils.formatDate(event.getStartDate(), DATE_FORMAT));
            addParam(params, VISIT_DOCTOR_ID_PARAM, event.getDoctor().getId());
            addParam(params, VISIT_COMPLAINT_PARAM, event.getClient().getReason());
            String url = JsfUtils.buildUrl(jdentUrl + START_DENTAL_VISIT_URL, params);
            JsfUtils.externalRedirect(url);
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
        Map<String, Object> params = new HashMap<>();
        params.put(PATIENT_ID_PARAM, event.getClient().getJdentPatientId());
        String url = JsfUtils.buildUrl(configBean.getJdentUrl() + GO_TO_CARD_URL, params);
        JsfUtils.externalRedirect(url);
    }

    /**
     * Edits schedule event.
     *
     * @return path for navigating
     */
    public String editEvent() {
        setEmployeeListBeanParams();
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
        PatientBean patientBean = JsfUtils.getSessionBean("patientBean");
        if (patientBean != null) {
            patientBean.setSearchPatientRequest(new SearchPatientRequest());
            patientBean.setPatient(null);
            patientBean.setEmployeeListBeanParams(workplace, event.getStartDate(), event.getEndDate());
            patientBean.search();
        } else {
            setEmployeeListBeanParams();
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
     * Selects view type listener.
     */
    public void selectViewTypeListener() {
        if (ScheduleViewType.WORKPLACE.equals(viewType)) {
            doctor = null;
        } else if (ScheduleViewType.EMPLOYEE.equals(viewType)) {
            workplace = null;
        }
        init();
        JsfUtils.update(SCHEDULER_COMPONENT);
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

    /**
     * Select doctor listener.
     */
    public void selectDoctorListener() {
        JsfUtils.update(SCHEDULER_COMPONENT);
    }

    /**
     * Start date select listener.
     *
     * @param selectEvent date select event
     */
    public void startDateSelectListener(SelectEvent selectEvent) {
        initEmployeeListBean();
    }

    /**
     * End date select listener.
     *
     * @param selectEvent date select event
     */
    public void endDateSelectListener(SelectEvent selectEvent) {
        initEmployeeListBean();
    }

    /**
     * Workplace select listener.
     */
    public void workplaceSelectListener() {
        initEmployeeListBean();
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

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
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

    public ScheduleViewType getViewType() {
        return viewType;
    }

    public void setViewType(ScheduleViewType viewType) {
        this.viewType = viewType;
    }

    public void setResourceService(IAvailableResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setQuotaService(IQuotaService quotaService) {
        this.quotaService = quotaService;
    }

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
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
        if (doctor != null || workplace != null) {
            Date startDate = new Timestamp(((Date) selectEvent.getObject()).getTime());
            Date endDate = new Timestamp(DateUtils.addMinuteToDate(startDate, configBean.getEventDuration()).getTime());
//TODO: use setting checkQuotas = true
//            String doctorId = ScheduleViewType.EMPLOYEE.equals(viewType) ? doctor.getId() : null;
//            String workplaceId = ScheduleViewType.WORKPLACE.equals(viewType) ? workplace.getId() : null;
//            Quota workPeriod = quotaService.getWorkPeriod(startDate, endDate, doctorId, workplaceId);
//            if (workPeriod != null) {
//                Schedule scheduleEvent = new Schedule(startDate, endDate, EMPTY);
//                scheduleEvent.setDoctor(workPeriod.getTimetable().getAvailableResource().getDoctor());
//                scheduleEvent.setWorkplace(workPeriod.getTimetable().getAvailableResource().getWorkplace());
//                return scheduleEvent;
//            }
            Schedule scheduleEvent = new Schedule(startDate, endDate, EMPTY);
            scheduleEvent.setDoctor(doctor);
            scheduleEvent.setWorkplace(workplace);
            return scheduleEvent;
        }
        return null;
    }

    private ScheduleSearchCriteria buildScheduleSearchCriteria(Date start, Date end) {
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        if (workplace != null) {
            searchCriteria.setWorkplace(workplace);
        }
        if (doctor != null) {
            searchCriteria.setDoctor(doctor);
        }
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

    private void initEmployeeListBean() {
        EmployeeListBean employeeListBean = JsfUtils.getViewBean("employeeListBean");
        if (employeeListBean != null) {
            employeeListBean.init(workplace, event.getStartDate(), event.getEndDate());
        }
    }

    private void setEmployeeListBeanParams() {
        JsfUtils.setFlashAttribute(WORKPLACE_PARAM, workplace);
        JsfUtils.setFlashAttribute(START_DATE_PARAM, event.getStartDate());
        JsfUtils.setFlashAttribute(END_DATE_PARAM, event.getEndDate());
    }

    private void fillQuotas() {
        AvailableResourceSearchCriteria resourceCriteria = new AvailableResourceSearchCriteria();
        if (ScheduleViewType.EMPLOYEE.equals(viewType)) {
            resourceCriteria.setDoctor(doctor);
        } else {
            resourceCriteria.setWorkplace(workplace);
        }
        List<AvailableResource> resources = resourceService.getByCriteria(resourceCriteria);
        for (AvailableResource resource : resources) {
            resource = (AvailableResource) resourceService.getById(resource.getId());
            for (ResourceTimetable timetable : resource.getTimetables()) {
                if (TimetableStatus.APPROVED.equals(timetable.getStatus())) {
                    for (Quota quota : timetable.getQuotas()) {
                        eventModel.getEvents().add(createScheduleEvent(quota));
                    }
                }
            }
        }
    }

    private ScheduleEvent createScheduleEvent(Quota quota) {
        DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(
            StringUtils.EMPTY,
            quota.getStartDate(),
            quota.getEndDate(),
            QuotaType.WORK_TIME.equals(quota.getQuotaType()) ? WORK_TIME_STYLE_CLASS : OFF_TIME_STYLE_CLASS);
        scheduleEvent.setId(quota.getId());
        scheduleEvent.setData(StringUtils.EMPTY);
        scheduleEvent.setEditable(false);
        scheduleEvent.setDescription(propertyReader.readProperty(quota.getQuotaType().getValue()));
        return scheduleEvent;
    }

    private void addParam(Map<String, Object> params, String key, Object value) {
        if (value != null) {
            params.put(key, value);
        }
    }

}
