package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.TimetableTemplate;
import com.zburzhynski.jplanner.impl.domain.Quota;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Timetable template bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TemplateBean implements Serializable {

    private Date startDate;

    private Date endDate;

    private TimetableTemplate template = TimetableTemplate.DAY_OF_WEEK;

    private List<Quota> quotas;

    private String[] selectedDayOfWeek;

    private String[] selectedDayOfMonth;

    private List<Date> selectedArbitraryDays;

    private String[] excludedDayOfWeek;

    private List<Date> excludedArbitraryDays;

    /**
     * Generates time quotes.
     */
    public void generate() {

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

    public List<Quota> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<Quota> quotas) {
        this.quotas = quotas;
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

    public List<Date> getSelectedArbitraryDays() {
        return selectedArbitraryDays;
    }

    public void setSelectedArbitraryDays(List<Date> selectedArbitraryDays) {
        this.selectedArbitraryDays = selectedArbitraryDays;
    }

    public String[] getExcludedDayOfWeek() {
        return excludedDayOfWeek;
    }

    public void setExcludedDayOfWeek(String[] excludedDayOfWeek) {
        this.excludedDayOfWeek = excludedDayOfWeek;
    }

    public List<Date> getExcludedArbitraryDays() {
        return excludedArbitraryDays;
    }

    public void setExcludedArbitraryDays(List<Date> excludedArbitraryDays) {
        this.excludedArbitraryDays = excludedArbitraryDays;
    }

}
