package com.zburzhynski.jplanner.impl.service;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.RUSSIAN_DATE_FORMAT;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.TIME_FORMAT;
import static com.zburzhynski.jplanner.api.domain.TimetableTemplate.DAY_OF_WEEK;
import com.zburzhynski.jplanner.api.criteria.TimetableCreateCriteria;
import com.zburzhynski.jplanner.api.domain.DayOfWeek;
import com.zburzhynski.jplanner.api.repository.IEmployeeRepository;
import com.zburzhynski.jplanner.api.service.ITimetableService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of {@link ITimetableService} interface.
 * <p/>
 * Date: 04.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("timetableService")
public class TimetableService implements ITimetableService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = false)
    public void createTimetable(TimetableCreateCriteria createCriteria) {
        Date startDate = createCriteria.getStartDate();
        Date endDate = createCriteria.getEndDate();
        List<Quota> quotas = new ArrayList<>();
        while (startDate.before(endDate)) {
            createDayOfWeekQuotas(startDate, createCriteria, quotas);
            startDate = DateUtils.addDayToDate(startDate, 1);
        }
        if (CollectionUtils.isEmpty(quotas)) {
            return;
        }
        Employee employee = (Employee) employeeRepository.findById(createCriteria.getEmployeeId());
        Timetable timetable = new Timetable();
        timetable.setStartDate(createCriteria.getStartDate());
        timetable.setEndDate(createCriteria.getEndDate());
        timetable.setDescription(createCriteria.getDescription());
        timetable.setQuotas(quotas);
        employee.getTimetables().add(timetable);
        employeeRepository.saveOrUpdate(employee);
    }

    private void createDayOfWeekQuotas(Date date, TimetableCreateCriteria createCriteria, List<Quota> quotas) {
        if (DAY_OF_WEEK.equals(createCriteria.getTemplate())) {
            if (!isDateExcluded(date, createCriteria) && isDateInList(date, createCriteria.getSelectedDayOfWeek())) {
                quotas.addAll(createQuotas(date, createCriteria));
            }
        }
    }

    private List<Quota> createQuotas(Date currentDate, TimetableCreateCriteria createCriteria) {
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

    private boolean isDateInList(Date date, String[] selectedDayOfWeek) {
        for (String selectedDay : selectedDayOfWeek) {
            if (DateUtils.getDayName(date).equals(DayOfWeek.valueOf(selectedDay))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDateExcluded(Date date, TimetableCreateCriteria createCriteria) {
        if (isDateInList(date, createCriteria.getExcludedDayOfWeek())) {
            return true;
        }
        for (Date excludedDate : createCriteria.getExcludedArbitraryDates()) {
            if (DateUtils.isSameDay(date, excludedDate)) {
                return true;
            }
        }
        return false;
    }

}
