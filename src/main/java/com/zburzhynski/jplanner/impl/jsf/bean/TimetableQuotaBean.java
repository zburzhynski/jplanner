package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.TIMETABLE_QUOTA;
import static com.zburzhynski.jplanner.api.domain.View.TIMETABLE_QUOTAS;
import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.RESOURCE_ID_PARAM;
import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.TIMETABLE_ID_PARAM;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IQuotaService;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.jsf.validator.TimetableQuotaValidator;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Timetable quota bean.
 * <p/>
 * Date: 07.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class TimetableQuotaBean implements Serializable {

    private static final String QUOTA_WORK_TIME_CLASS = "quotaWorkTime";

    private static final String QUOTA_OFF_TIME_CLASS = "quotaOffTime";

    private static final String QUOTA_HAS_SCHEDULES = "quotas.quotaHasSchedules";

    private static final String RESOURCE_UNAVAILABLE = "quotas.resourceUnavailable";

    private String resourceId;

    private String timetableId;

    private ScheduleEvent event;

    private ScheduleModel eventModel;

    private Quota quota;

    @ManagedProperty(value = "#{resourceTimetableService}")
    private IResourceTimetableService timetableService;

    @ManagedProperty(value = "#{quotaService}")
    private IQuotaService quotaService;

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{timetableQuotaValidator}")
    private TimetableQuotaValidator quotaValidator;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        resourceId = (String) JsfUtils.getFlashAttribute(RESOURCE_ID_PARAM);
        timetableId = (String) JsfUtils.getFlashAttribute(TIMETABLE_ID_PARAM);
        ResourceTimetable timetable = (ResourceTimetable) timetableService.getById(timetableId);
        eventModel = new DefaultScheduleModel();
        if (CollectionUtils.isNotEmpty(timetable.getQuotas())) {
            for (Quota timetableQuota : timetable.getQuotas()) {
                eventModel.getEvents().add(createQuotaEvent(timetableQuota));
            }
        }
    }

    /**
     * Creates quota event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void createEvent(SelectEvent selectEvent) {
        quota = buildQuota(selectEvent);
        JsfUtils.redirect(TIMETABLE_QUOTA.getPath());
    }

    /**
     * Selects quota event.
     *
     * @param selectEvent {@link SelectEvent}
     */
    public void selectEvent(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        quota = (Quota) quotaService.getById(event.getId());
        if (quota != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('menu').show();");
        }
    }

    /**
     * Edits quota event.
     *
     * @return path for navigating
     */
    public String editQuota() {
        return TIMETABLE_QUOTA.getPath();
    }

    /**
     * Moves quota event.
     *
     * @param moveEvent {@link ScheduleEntryMoveEvent}
     */
    public void moveEvent(ScheduleEntryMoveEvent moveEvent) {
        ScheduleEvent scheduleEvent = moveEvent.getScheduleEvent();
        quota = (Quota) quotaService.getById(scheduleEvent.getId());
        Date originalStartDate = quota.getStartDate();
        Date originalEndDate = quota.getEndDate();
        quota.setStartDate(scheduleEvent.getStartDate());
        quota.setEndDate(scheduleEvent.getEndDate());
        boolean valid = quotaValidator.validate(quota);
        if (!valid) {
            quota.setStartDate(originalStartDate);
            quota.setEndDate(originalEndDate);
            eventModel.updateEvent(createQuotaEvent(quota));
            return;
        }
        quotaService.saveOrUpdate(quota);
    }

    /**
     * Resize quota event.
     *
     * @param resizeEvent {@link ScheduleEntryResizeEvent}
     */
    public void resizeEvent(ScheduleEntryResizeEvent resizeEvent) {
        ScheduleEvent scheduleEvent = resizeEvent.getScheduleEvent();
        quota = (Quota) quotaService.getById(scheduleEvent.getId());
        boolean valid = quotaValidator.validate(quota);
        quota.setEndDate(scheduleEvent.getEndDate());
        if (!valid) {
            return;
        }
        quotaService.saveOrUpdate(quota);
    }

    /**
     * Saves quota event.
     *
     * @return path for navigating
     */
    public String saveQuota() {
        if (!timetableService.isQuotaPeriodAvailable(quota.getTimetable(), quota)) {
            addMessage(RESOURCE_UNAVAILABLE);
            return null;
        }
        boolean quotaAdd = isBlank(quota.getId());
        quotaService.saveOrUpdate(quota);
        if (quotaAdd) {
            eventModel.addEvent(createQuotaEvent(quota));
            eventModel.getEvents().get(eventModel.getEvents().size() - 1).setId(quota.getId());
        } else {
            eventModel.updateEvent(createQuotaEvent(quota));
        }
        return TIMETABLE_QUOTAS.getPath();
    }

    /**
     * Cancel updates quota.
     *
     * @return path for navigating
     */
    public String cancelUpdateQuota() {
        return TIMETABLE_QUOTAS.getPath();
    }

    /**
     * Removes quota event.
     *
     * @return path for navigating
     */
    public String removeQuota() {
        quota = (Quota) quotaService.getById(quota.getId());
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        searchCriteria.setStartDate(quota.getStartDate());
        searchCriteria.setEndDate(quota.getEndDate());
        searchCriteria.setDoctor(quota.getTimetable().getAvailableResource().getDoctor());
        searchCriteria.setWorkplace(quota.getTimetable().getAvailableResource().getWorkplace());
        if (scheduleService.countByCriteria(searchCriteria) > 0) {
            addMessage(QUOTA_HAS_SCHEDULES);
            return null;
        }
        eventModel.deleteEvent(event);
        quotaService.delete(quota);
        return TIMETABLE_QUOTAS.getPath();
    }

    /**
     * Returns to timetables form.
     *
     * @return path for navigating
     */
    public String back() {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        return View.TIMETABLES.getPath();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Quota getQuota() {
        return quota;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
    }

    public void setTimetableService(IResourceTimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public void setQuotaService(IQuotaService quotaService) {
        this.quotaService = quotaService;
    }

    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setQuotaValidator(TimetableQuotaValidator quotaValidator) {
        this.quotaValidator = quotaValidator;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    private ScheduleEvent createQuotaEvent(Quota value) {
        String quotaType = propertyReader.readProperty(value.getQuotaType().getValue());
        ScheduleEvent scheduleEvent = new DefaultScheduleEvent(
            quotaType,
            value.getStartDate(),
            value.getEndDate(),
            QuotaType.WORK_TIME.equals(value.getQuotaType()) ? QUOTA_WORK_TIME_CLASS : QUOTA_OFF_TIME_CLASS);
        scheduleEvent.setId(value.getId());
        return scheduleEvent;
    }

    private Quota buildQuota(SelectEvent selectEvent) {
        Date startDate = new Timestamp(((Date) selectEvent.getObject()).getTime());
        Date endDate = new Timestamp(DateUtils.addMinuteToDate(startDate, configBean.getEventDuration()).getTime());
        Quota value = new Quota(startDate, endDate, null);
        value.setTimetable((ResourceTimetable) timetableService.getById(timetableId));
        return value;
    }

    /**
     * Adds localisation message to context.
     *
     * @param message localisation message
     */
    private void addMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(propertyReader.readProperty(message), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

}
