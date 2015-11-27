package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.IOrganizationTimetableService;
import com.zburzhynski.jplanner.impl.domain.OrganizationTimetable;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Organization timetable bean.
 * <p/>
 * Date: 27.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class OrganizationTimetablesBean implements Serializable {

    private List<OrganizationTimetable> timetables;

    @ManagedProperty(value = "#{organizationTimetableService}")
    private IOrganizationTimetableService timetableService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        timetables = timetableService.getAll();
    }

    public List<OrganizationTimetable> getTimetables() {
        return timetables;
    }

    public void setTimetableService(IOrganizationTimetableService timetableService) {
        this.timetableService = timetableService;
    }

}
