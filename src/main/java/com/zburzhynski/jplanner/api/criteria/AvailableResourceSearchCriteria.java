package com.zburzhynski.jplanner.api.criteria;

import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Workplace;

import java.util.Date;
import java.util.List;

/**
 * Available resource search criteria.
 * <p/>
 * Date: 17.10.2015
 *
 * @author Vladimir Zburzhynski
 */
public class AvailableResourceSearchCriteria {

    private Workplace workplace;

    private Employee doctor;

    private Employee assistant;

    private Date quotaStartDate;

    private Date quotaEndDate;

    private List<String> quotaIds;

    private Long start;

    private Long end;

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

    public Employee getAssistant() {
        return assistant;
    }

    public void setAssistant(Employee assistant) {
        this.assistant = assistant;
    }

    public Date getQuotaStartDate() {
        return quotaStartDate;
    }

    public void setQuotaStartDate(Date quotaStartDate) {
        this.quotaStartDate = quotaStartDate;
    }

    public Date getQuotaEndDate() {
        return quotaEndDate;
    }

    public void setQuotaEndDate(Date quotaEndDate) {
        this.quotaEndDate = quotaEndDate;
    }

    public List<String> getQuotaIds() {
        return quotaIds;
    }

    public void setQuotaIds(List<String> quotaIds) {
        this.quotaIds = quotaIds;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

}
