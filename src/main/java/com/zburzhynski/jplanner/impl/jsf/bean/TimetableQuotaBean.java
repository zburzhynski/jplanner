package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.RESOURCE_ID_PARAM;
import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.TIMETABLE_ID_PARAM;
import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IQuotaService;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.jsf.validator.TimetableQuotaValidator;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Timetable quota bean.
 * <p/>
 * Date: 07.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TimetableQuotaBean implements Serializable {

    private static final String QUOTA_WORK_TIME_CLASS = "quotaWorkTime";

    private static final String QUOTA_OFF_TIME_CLASS = "quotaOffTime";

    private String resourceId;

    private ScheduleModel eventModel;

    @ManagedProperty(value = "#{resourceTimetableService}")
    private IResourceTimetableService timetableService;

    @ManagedProperty(value = "#{quotaService}")
    private IQuotaService quotaService;

    @ManagedProperty(value = "#{timetableQuotaValidator}")
    private TimetableQuotaValidator quotaValidator;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        resourceId = (String) JsfUtils.getFlashAttribute(RESOURCE_ID_PARAM);
        String timetableId = (String) JsfUtils.getFlashAttribute(TIMETABLE_ID_PARAM);
        ResourceTimetable timetable = (ResourceTimetable) timetableService.getById(timetableId);
        eventModel = new DefaultScheduleModel();
        if (CollectionUtils.isNotEmpty(timetable.getQuotas())) {
            for (Quota quota : timetable.getQuotas()) {
                eventModel.getEvents().add(createQuotaEvent(quota));
            }
        }
    }

    /**
     * Moves quota event.
     *
     * @param moveEvent {@link ScheduleEntryMoveEvent}
     */
    public void moveEvent(ScheduleEntryMoveEvent moveEvent) {
        ScheduleEvent scheduleEvent = moveEvent.getScheduleEvent();
        Quota quota = (Quota) quotaService.getById(scheduleEvent.getId());
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
        Quota quota = (Quota) quotaService.getById(scheduleEvent.getId());
        boolean valid = quotaValidator.validate(quota);
        quota.setEndDate(scheduleEvent.getEndDate());
        if (!valid) {
            return;
        }
        quotaService.saveOrUpdate(quota);
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

    public void setTimetableService(IResourceTimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public void setQuotaService(IQuotaService quotaService) {
        this.quotaService = quotaService;
    }

    public void setQuotaValidator(TimetableQuotaValidator quotaValidator) {
        this.quotaValidator = quotaValidator;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    private ScheduleEvent createQuotaEvent(Quota quota) {
        String quotaType = propertyReader.readProperty(quota.getQuotaType().getValue());
        ScheduleEvent event = new DefaultScheduleEvent(
            quotaType,
            quota.getStartDate(),
            quota.getEndDate(),
            QuotaType.WORK_TIME.equals(quota.getQuotaType()) ? QUOTA_WORK_TIME_CLASS : QUOTA_OFF_TIME_CLASS);
        event.setId(quota.getId());
        return event;
    }

}
