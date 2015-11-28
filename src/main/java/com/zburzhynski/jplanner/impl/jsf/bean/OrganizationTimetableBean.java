package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.TIME_FORMAT;
import com.zburzhynski.jplanner.api.domain.DayOfWeek;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IOrganizationTimetableService;
import com.zburzhynski.jplanner.impl.domain.OrganizationTimetable;
import com.zburzhynski.jplanner.impl.util.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * Organization timetable bean.
 * <p/>
 * Date: 27.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ApplicationScoped
public class OrganizationTimetableBean implements Serializable {

    private OrganizationTimetable timetable;

    private SortedSet<OrganizationTimetable> timetables;

    private Map<DayOfWeek, OrganizationTimetable> timetableMap;

    @ManagedProperty(value = "#{organizationTimetableService}")
    private IOrganizationTimetableService timetableService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        List<OrganizationTimetable> all = timetableService.getAll();
        timetables = new TreeSet<>(all);
        timetableMap = new HashMap<>();
        for (OrganizationTimetable item : timetables) {
            timetableMap.put(item.getDayOfWeek(), item);
        }
    }

    /**
     * Saves organization timetable.
     *
     * @return path for navigating
     */
    public String saveTimetable() {
        timetableService.saveOrUpdate(timetable);
        init();
        return View.ORGANIZATION_TIMETABLES.getPath();
    }

    /**
     * Gets organization timetables by all days.
     *
     * @return organization timetables by all days
     */
    public SortedSet<OrganizationTimetable> getTimetables() {
        return timetables;
    }

    /**
     * Gets organization timetable by day.
     *
     * @param day day of week
     * @return organization timetable
     */
    public OrganizationTimetable getByDay(DayOfWeek day) {
        return timetableMap.get(day);
    }

    /**
     * Gets min time from all days of week.
     *
     * @return time
     */
    public String getMinTime() {
        SortedSet<Date> times = new TreeSet<>();
        for (OrganizationTimetable item : timetables) {
            times.add(item.getStartTime());
        }
        return DateUtils.formatDate(times.first(), TIME_FORMAT) ;
    }

    /**
     * Gets max time from all days of week.
     *
     * @return time
     */
    public String getMaxTime() {
        SortedSet<Date> times = new TreeSet<>();
        for (OrganizationTimetable item : timetables) {
            times.add(item.getEndTime());
        }
        return DateUtils.formatDate(times.first(), TIME_FORMAT) ;
    }

    public OrganizationTimetable getTimetable() {
        return timetable;
    }

    public void setTimetable(OrganizationTimetable timetable) {
        this.timetable = timetable;
    }

    public void setTimetableService(IOrganizationTimetableService timetableService) {
        this.timetableService = timetableService;
    }

}
