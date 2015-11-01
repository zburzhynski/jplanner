package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.ModificationMode.CREATE;
import static com.zburzhynski.jplanner.api.domain.ModificationMode.EDIT;
import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.RESOURCE_ID_PARAM;
import static com.zburzhynski.jplanner.impl.jsf.bean.TimetablesBean.TIMETABLE_ID_PARAM;
import com.zburzhynski.jplanner.api.criteria.QuotaCreateCriteria;
import com.zburzhynski.jplanner.api.domain.CommonConstant;
import com.zburzhynski.jplanner.api.domain.ModificationMode;
import com.zburzhynski.jplanner.api.domain.TimetableTemplate;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.ITimetableService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.jsf.validator.QuotaValidator;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
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

    private Quota quota = new Quota();

    private Set<Quota> quotas = new TreeSet<>();

    private String[] selectedDayOfWeek;

    private String[] selectedDayOfMonth;

    private Set<Date> selectedArbitraryDates = new TreeSet<>();

    private String[] excludedDayOfWeek;

    private Set<Date> excludedArbitraryDates = new TreeSet<>();

    private String description;

    private ModificationMode quotaModificationMode;

    private String timetableId;

    private String resourceId;

    @ManagedProperty(value = "#{timetableService}")
    private ITimetableService timetableService;

    @ManagedProperty(value = "#{quotaValidator}")
    private QuotaValidator quotaValidator;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        timetableId = (String) JsfUtils.getFlashAttribute(TIMETABLE_ID_PARAM);
        resourceId = (String) JsfUtils.getFlashAttribute(RESOURCE_ID_PARAM);
    }

    /**
     * Generates time quotes.
     *
     * @return path for navigating
     */
    public String generate() {
        QuotaCreateCriteria createCriteria = buildQuotaCreateCriteria();
        timetableService.createQuota(createCriteria);
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        return View.TIMETABLES.getPath();
    }

    /**
     * Cancel generating.
     *
     * @return path for navigating
     */
    public String cancel() {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        return View.TIMETABLES.getPath();
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
        boolean valid = quotaValidator.validate(quota, new ArrayList<>(quotas));
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
     *
     * @param removedQuota removed quota
     */
    public void removeQuota(Quota removedQuota) {
        quotas.remove(removedQuota);
    }

    /**
     * Arbitrary date select listener.
     *
     * @param event {SelectEvent} select event
     */
    public void arbitraryDateSelectListener(SelectEvent event) {
        Date selected = (Date) event.getObject();
        if (selectedArbitraryDates.contains(selected)) {
            selectedArbitraryDates.remove(selected);
        } else {
            selectedArbitraryDates.add(selected);
        }
    }

    /**
     * Excluded date select listener.
     *
     * @param event {SelectEvent} select event
     */
    public void excludedDateSelectListener(SelectEvent event) {
        Date selected = (Date) event.getObject();
        if (excludedArbitraryDates.contains(selected)) {
            excludedArbitraryDates.remove(selected);
        } else {
            excludedArbitraryDates.add(selected);
        }
    }

    /**
     * Clear selected arbitrary dates.
     */
    public void clearSelectedArbitraryDates() {
        selectedArbitraryDates = new TreeSet<>();
    }

    /**
     * Clear excluded arbitrary dates.
     */
    public void clearExcludedArbitraryDates() {
        excludedArbitraryDates = new TreeSet<>();
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

    public Set<Quota> getQuotas() {
        return quotas;
    }

    public void setQuotas(Set<Quota> quotas) {
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

    /**
     * Gets selected arbitrary dates.
     *
     * @return selected arbitrary dates
     */
    public String getSelectedArbitraryDates() {
        List<String> selectedDates = new ArrayList<>();
        for (Date date : selectedArbitraryDates) {
            selectedDates.add(DateUtils.formatDate(date, CommonConstant.RUSSIAN_DATE_FORMAT));
        }
        return StringUtils.join(selectedDates, CommonConstant.COMMA + CommonConstant.SPACE);
    }

    public String[] getExcludedDayOfWeek() {
        return excludedDayOfWeek;
    }

    public void setExcludedDayOfWeek(String[] excludedDayOfWeek) {
        this.excludedDayOfWeek = excludedDayOfWeek;
    }

    /**
     * Gets excluded arbitrary dates.
     *
     * @return excluded arbitrary dates
     */
    public String getExcludedArbitraryDates() {
        List<String> excludedDates = new ArrayList<>();
        for (Date date : excludedArbitraryDates) {
            excludedDates.add(DateUtils.formatDate(date, CommonConstant.RUSSIAN_DATE_FORMAT));
        }
        return StringUtils.join(excludedDates, CommonConstant.COMMA + CommonConstant.SPACE);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimetableService(ITimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public void setQuotaValidator(QuotaValidator quotaValidator) {
        this.quotaValidator = quotaValidator;
    }

    private QuotaCreateCriteria buildQuotaCreateCriteria() {
        QuotaCreateCriteria criteria = new QuotaCreateCriteria();
        criteria.setTimetableId(timetableId);
        criteria.setStartDate(startDate);
        criteria.setEndDate(endDate);
        criteria.setTemplate(template);
        criteria.setSelectedDayOfWeek(selectedDayOfWeek);
        criteria.setSelectedDayOfMonth(selectedDayOfMonth);
        criteria.setSelectedArbitraryDates(selectedArbitraryDates);
        criteria.setExcludedDayOfWeek(excludedDayOfWeek);
        criteria.setExcludedArbitraryDates(excludedArbitraryDates);
        criteria.setQuotas(quotas);
        criteria.setDescription(description);
        return criteria;
    }

}
