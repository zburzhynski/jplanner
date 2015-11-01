package com.zburzhynski.jplanner.api.criteria;

import com.zburzhynski.jplanner.api.domain.TimetableTemplate;
import com.zburzhynski.jplanner.impl.domain.Quota;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Quota create criteria.
 * <p/>
 * Date: 04.09.2015
 *
 * @author Vladimir Zburzhynski
 */
public class QuotaCreateCriteria {

    private String timetableId;

    private Date startDate;

    private Date endDate;

    private TimetableTemplate template;

    private String[] selectedDayOfWeek;

    private String[] selectedDayOfMonth;

    private Set<Date> selectedArbitraryDates = new TreeSet<>();

    private String[] excludedDayOfWeek;

    private Set<Date> excludedArbitraryDates = new TreeSet<>();

    private Set<Quota> quotas = new TreeSet<>();

    private String description;

    public String getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(String timetableId) {
        this.timetableId = timetableId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TimetableTemplate getTemplate() {
        return template;
    }

    public void setTemplate(TimetableTemplate template) {
        this.template = template;
    }

    public String[] getSelectedDayOfWeek() {
        return selectedDayOfWeek;
    }

    public void setSelectedDayOfWeek(String[] selectedDayOfWeek) {
        this.selectedDayOfWeek = selectedDayOfWeek;
    }

    public String[] getSelectedDayOfMonth() {
        return selectedDayOfMonth;
    }

    public void setSelectedDayOfMonth(String[] selectedDayOfMonth) {
        this.selectedDayOfMonth = selectedDayOfMonth;
    }

    public Set<Date> getSelectedArbitraryDates() {
        return selectedArbitraryDates;
    }

    public void setSelectedArbitraryDates(Set<Date> selectedArbitraryDates) {
        this.selectedArbitraryDates = selectedArbitraryDates;
    }

    public String[] getExcludedDayOfWeek() {
        return excludedDayOfWeek;
    }

    public void setExcludedDayOfWeek(String[] excludedDayOfWeek) {
        this.excludedDayOfWeek = excludedDayOfWeek;
    }

    public Set<Date> getExcludedArbitraryDates() {
        return excludedArbitraryDates;
    }

    public void setExcludedArbitraryDates(Set<Date> excludedArbitraryDates) {
        this.excludedArbitraryDates = excludedArbitraryDates;
    }

    public Set<Quota> getQuotas() {
        return quotas;
    }

    public void setQuotas(Set<Quota> quotas) {
        this.quotas = quotas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
