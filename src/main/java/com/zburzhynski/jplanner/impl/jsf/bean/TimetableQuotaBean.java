package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.TIMETABLE_ID_PARAM;
import com.zburzhynski.jplanner.api.service.ITimetableService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        String timetableId = (String) JsfUtils.getFlashAttribute(TIMETABLE_ID_PARAM);
        Timetable timetable = (Timetable) timetableService.getById(timetableId);
        eventModel = new DefaultScheduleModel();
        if (CollectionUtils.isNotEmpty(timetable.getQuotas())) {
            List<ScheduleEvent> events = new ArrayList<>();
            for (Quota quota : timetable.getQuotas()) {
                ScheduleEvent event = new DefaultScheduleEvent(quota.getQuotaType().name(),
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

}
