package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.RESOURCE_ID_PARAM;
import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.TIMETABLE_PARAM;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Timetable bean.
 * <p/>
 * Date: 31.10.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TimetableBean implements Serializable {

    private String resourceId;

    private ResourceTimetable timetable;

    @ManagedProperty(value = "#{timetableService}")
    private IResourceTimetableService timetableService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        resourceId = (String) JsfUtils.getFlashAttribute(RESOURCE_ID_PARAM);
        ResourceTimetable editedTimetable = (ResourceTimetable) JsfUtils.getFlashAttribute(TIMETABLE_PARAM);
        if (editedTimetable == null) {
            AvailableResource resource = (AvailableResource) JsfUtils.getFlashAttribute(TimetablesBean.RESOURCE_PARAM);
            timetable = new ResourceTimetable();
            timetable.setAvailableResource(resource);
        } else {
            timetable = editedTimetable;
        }
    }

    /**
     * Saves timetable.
     *
     * @return path for navigating
     */
    public String saveTimetable() {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        timetableService.saveOrUpdate(timetable);
        return View.TIMETABLES.getPath();
    }

    /**
     * Cancel update template.
     *
     * @return path for navigating
     */
    public String cancelUpdateTimetable() {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        return View.TIMETABLES.getPath();
    }

    public ResourceTimetable getTimetable() {
        return timetable;
    }

    public void setTimetable(ResourceTimetable timetable) {
        this.timetable = timetable;
    }

    public void setTimetableService(IResourceTimetableService timetableService) {
        this.timetableService = timetableService;
    }

}
