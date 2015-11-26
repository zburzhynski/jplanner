package com.zburzhynski.jplanner.impl.service;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.RUSSIAN_DATE_FORMAT;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.TIME_FORMAT;
import static com.zburzhynski.jplanner.api.domain.TimetableTemplate.ARBITRARY_DATE;
import static com.zburzhynski.jplanner.api.domain.TimetableTemplate.DAY_OF_MONTH;
import static com.zburzhynski.jplanner.api.domain.TimetableTemplate.DAY_OF_WEEK;
import static com.zburzhynski.jplanner.api.domain.TimetableTemplate.EVEN_DAY;
import static com.zburzhynski.jplanner.api.domain.TimetableTemplate.ODD_DAY;
import com.zburzhynski.jplanner.api.criteria.QuotaCreateCriteria;
import com.zburzhynski.jplanner.api.domain.DayOfMonth;
import com.zburzhynski.jplanner.api.domain.DayOfWeek;
import com.zburzhynski.jplanner.api.repository.IResourceTimetableRepository;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Implementation of {@link IResourceTimetableService} interface.
 * <p/>
 * Date: 04.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("resourceTimetableService")
@Transactional(readOnly = true)
public class ResourceTimetableService implements IResourceTimetableService<String, ResourceTimetable> {

    @Autowired
    private IResourceTimetableRepository timetableRepository;

    @Override
    public ResourceTimetable getById(String id) {
        return (ResourceTimetable) timetableRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(ResourceTimetable timetable) {
        boolean result = false;
        if (timetable != null) {
            if (StringUtils.isBlank(timetable.getId())) {
                timetableRepository.insert(timetable);
                result = true;
            } else {
                timetableRepository.update(timetable);
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(ResourceTimetable timetable) {
        boolean deleted = false;
        if (timetable != null) {
            timetableRepository.delete(timetable);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<ResourceTimetable> getAll() {
        return timetableRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void createQuota(QuotaCreateCriteria criteria) {
        Date startDate = criteria.getStartDate();
        Date endDate = criteria.getEndDate();
        SortedSet<Quota> quotas = new TreeSet<>();
        while (startDate.before(endDate)) {
            createDayOfWeekQuotas(startDate, criteria, quotas);
            createEvenDayQuotas(startDate, criteria, quotas);
            createOddDayQuotas(startDate, criteria, quotas);
            createDayOfMonthQuotas(startDate, criteria, quotas);
            createArbitraryDateQuotas(startDate, criteria, quotas);
            startDate = DateUtils.addDayToDate(startDate, 1);
        }
        if (CollectionUtils.isEmpty(quotas)) {
            return;
        }
        ResourceTimetable timetable = (ResourceTimetable) timetableRepository.findById(criteria.getTimetableId());
        if (timetable == null) {
            return;
        }
        timetable.setStartDate(quotas.first().getStartDate());
        timetable.setEndDate(quotas.last().getEndDate());
        timetable.setDescription(criteria.getDescription());
        for (Quota quota : quotas) {
            timetable.addQuota(quota);
        }
        timetableRepository.saveOrUpdate(timetable);
    }

    private void createDayOfWeekQuotas(Date date, QuotaCreateCriteria criteria, Set<Quota> quotas) {
        if (DAY_OF_WEEK.equals(criteria.getTemplate())) {
            if (!isDateExcluded(date, criteria) && isDayInWeek(date, criteria.getSelectedDayOfWeek())) {
                quotas.addAll(createQuotas(date, criteria));
            }
        }
    }

    private void createEvenDayQuotas(Date date, QuotaCreateCriteria criteria, Set<Quota> quotas) {
        if (EVEN_DAY.equals(criteria.getTemplate())) {
            if (!isDateExcluded(date, criteria) && DateUtils.isEvenDay(date)) {
                quotas.addAll(createQuotas(date, criteria));
            }
        }
    }

    private void createOddDayQuotas(Date date, QuotaCreateCriteria criteria, Set<Quota> quotas) {
        if (ODD_DAY.equals(criteria.getTemplate())) {
            if (!isDateExcluded(date, criteria) && DateUtils.isOddDay(date)) {
                quotas.addAll(createQuotas(date, criteria));
            }
        }
    }

    private void createDayOfMonthQuotas(Date date, QuotaCreateCriteria criteria, Set<Quota> quotas) {
        if (DAY_OF_MONTH.equals(criteria.getTemplate())) {
            if (!isDateExcluded(date, criteria) && isDayInMonth(date, criteria.getSelectedDayOfMonth())) {
                quotas.addAll(createQuotas(date, criteria));
            }
        }
    }

    private void createArbitraryDateQuotas(Date date, QuotaCreateCriteria criteria, Set<Quota> quotas) {
        if (ARBITRARY_DATE.equals(criteria.getTemplate())) {
            if (!isDateExcluded(date, criteria) && isDateInList(date, criteria.getSelectedArbitraryDates())) {
                quotas.addAll(createQuotas(date, criteria));
            }
        }
    }

    private List<Quota> createQuotas(Date currentDate, QuotaCreateCriteria createCriteria) {
        List<Quota> quotas = new ArrayList<>();
        String date = DateUtils.formatDate(currentDate, RUSSIAN_DATE_FORMAT);
        for (Quota templateQuota : createCriteria.getQuotas()) {
            String startTime = DateUtils.formatDate(templateQuota.getStartDate(), TIME_FORMAT);
            String endTime = DateUtils.formatDate(templateQuota.getEndDate(), TIME_FORMAT);
            String dateTimeTemplate = RUSSIAN_DATE_FORMAT + SPACE + TIME_FORMAT;
            Date startDate = DateUtils.parseDate(date + SPACE + startTime, dateTimeTemplate);
            Date endDate = DateUtils.parseDate(date + SPACE + endTime, dateTimeTemplate);
            Quota quota = new Quota();
            quota.setStartDate(startDate);
            quota.setEndDate(endDate);
            quota.setQuotaType(templateQuota.getQuotaType());
            quota.setDescription(templateQuota.getDescription());
            quotas.add(quota);
        }
        return quotas;
    }

    private boolean isDayInWeek(Date date, String[] selectedDayOfWeek) {
        for (String selectedDay : selectedDayOfWeek) {
            if (DateUtils.getDayName(date).equals(DayOfWeek.valueOf(selectedDay))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDayInMonth(Date date, String[] selectedDayOfMonth) {
        for (String selectedDay : selectedDayOfMonth) {
            if (DateUtils.extractDay(date) == DayOfMonth.valueOf(selectedDay).getNumber()) {
                return true;
            }
        }
        return false;
    }

    private boolean isDateExcluded(Date date, QuotaCreateCriteria createCriteria) {
        return isDayInWeek(date, createCriteria.getExcludedDayOfWeek())
            || isDateInList(date, createCriteria.getExcludedArbitraryDates());
    }

    private boolean isDateInList(Date date, Set<Date> selectedDates) {
        for (Date selectedDate : selectedDates) {
            if (DateUtils.isSameDay(date, selectedDate)) {
                return true;
            }
        }
        return false;
    }

}
