package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.TIMETABLE_ID_PARAM;
import com.zburzhynski.jplanner.api.service.ITimetableService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
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

    private ScheduleModel eventModel;

    @ManagedProperty(value = "#{timetableService}")
    private ITimetableService timetableService;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        String timetableId = (String) JsfUtils.getFlashAttribute(TIMETABLE_ID_PARAM);
        Timetable timetable = (Timetable) timetableService.getById(timetableId);
        eventModel = new DefaultScheduleModel();
        if (CollectionUtils.isNotEmpty(timetable.getQuotas())) {
            for (Quota quota : timetable.getQuotas()) {
                String quotaType = propertyReader.readProperty(quota.getQuotaType().getValue());
                ScheduleEvent event = new DefaultScheduleEvent(quotaType,
                    quota.getStartDate(), quota.getEndDate());
                event.setId(quota.getId());
                eventModel.addEvent(event);
            }
        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setTimetableService(ITimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

}
