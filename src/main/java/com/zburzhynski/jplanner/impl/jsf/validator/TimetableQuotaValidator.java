package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.service.IQuotaService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Timetable quota validator.
 * <p/>
 * Date: 22.11.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class TimetableQuotaValidator extends BaseValidator {

    private static final String QUOTA_HAS_SCHEDULES = "timetableQuotaValidator.quotaHasSchedules";

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IQuotaService quotaService;

    /**
     * Validates timetable quota.
     *
     * @param quota quota to validates
     * @return true if valid, else false
     */
    public boolean validate(Quota quota) {
        Quota previousQuota = (Quota) quotaService.getById(quota.getId());
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        searchCriteria.setStartDate(previousQuota.getStartDate());
        searchCriteria.setEndDate(previousQuota.getEndDate());
        searchCriteria.setDoctor(previousQuota.getTimetable().getAvailableResource().getDoctor());
        searchCriteria.setWorkplace(previousQuota.getTimetable().getAvailableResource().getWorkplace());
        List<Schedule> schedules = scheduleService.getByCriteria(searchCriteria);
        for (Schedule schedule : schedules) {
            if (schedule.getStartDate().before(quota.getStartDate()) ||
                schedule.getEndDate().after(quota.getEndDate())) {
                addMessage(QUOTA_HAS_SCHEDULES);
                return false;
            }
        }
        return true;
    }

}
