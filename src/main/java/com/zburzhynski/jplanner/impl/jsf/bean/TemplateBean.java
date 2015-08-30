package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.ModificationMode.CREATE;
import static com.zburzhynski.jplanner.api.domain.ModificationMode.EDIT;
import com.zburzhynski.jplanner.api.domain.ModificationMode;
import com.zburzhynski.jplanner.api.domain.TimetableTemplate;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.jsf.validator.QuotaValidator;
import com.zburzhynski.jplanner.impl.util.JsfUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

    private static final String HIDE_QUOTA_DIALOG = "PF('quota').hide();";

    private Date startDate;

    private Date endDate;

    private TimetableTemplate template = TimetableTemplate.DAY_OF_WEEK;

    private Quota quota;

    private List<Quota> quotas = new ArrayList<>();

    private String[] selectedDayOfWeek;

    private String[] selectedDayOfMonth;

    private List<Date> selectedArbitraryDays;

    private String[] excludedDayOfWeek;

    private List<Date> excludedArbitraryDays;

    private ModificationMode quotaModificationMode;

    @ManagedProperty(value = "#{quotaValidator}")
    private QuotaValidator quotaValidator;

    /**
     * Generates time quotes.
     */
    public void generate() {
    }

    /**
     * Adds quota.
     */
    public void addQuota() {
        quotaModificationMode = CREATE;
        quota = new Quota();
    }

    /**
     * Edits quota.
     */
    public void editQuota() {
        quotaModificationMode = EDIT;
    }

    /**
     * Saves quota.
     */
    public void saveQuota() {
        boolean valid = quotaValidator.validate(quota);
        if (!valid) {
            return;
        }
        if (CREATE.equals(quotaModificationMode)) {
            quotas.add(quota);
        }
        JsfUtils.execute(HIDE_QUOTA_DIALOG);
    }

    /**
     * Removes quota.
     */
    public void removeQuota() {
        quotas.remove(quota);
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

    public Quota getQuota() {
        return quota;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
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

    public void setQuotaValidator(QuotaValidator quotaValidator) {
        this.quotaValidator = quotaValidator;
    }

}
