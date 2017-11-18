package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.TimetableSearchCriteria;
import com.zburzhynski.jplanner.api.domain.TimetableStatus;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Timetable validator.
 * <p/>
 * Date: 18.11.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class TimetableValidator extends BaseValidator {

    private static final String TIMETABLE_HAS_SCHEDULES = "timetableValidator.timetableHasSchedules";

    private static final String TIMETABLE_TIME_OVERLAPPED = "timetableValidator.timetableTimeOverlapped";

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IResourceTimetableService timetableService;

    /**
     * Validates timetable.
     *
     * @param timetable {@link ResourceTimetable}
     * @return true if valid, else false
     */
    public boolean validate(ResourceTimetable timetable) {
        Set<Boolean> result = new HashSet<>();
        result.add(checkSchedules(timetable));
        result.add(checkPeriod(timetable));
        return !result.contains(false);
    }

    private boolean checkSchedules(ResourceTimetable timetable) {
        if (!isStatusChanged(timetable)) {
            return true;
        }
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        searchCriteria.setStartDate(timetable.getStartDate());
        searchCriteria.setEndDate(timetable.getEndDate());
        searchCriteria.setDoctor(timetable.getAvailableResource().getDoctor());
        searchCriteria.setWorkplace(timetable.getAvailableResource().getWorkplace());
        if (scheduleService.countByCriteria(searchCriteria) > 0) {
            addMessage(TIMETABLE_HAS_SCHEDULES);
            return false;
        }
        return true;
    }

    private boolean isStatusChanged(ResourceTimetable timetable) {
        if (timetable.getId() != null) {
            ResourceTimetable previousTimetable = (ResourceTimetable) timetableService.getById(timetable.getId());
            if (!timetable.getStatus().equals(previousTimetable.getStatus())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPeriod(ResourceTimetable timetable) {
        if (TimetableStatus.APPROVED.equals(timetable.getStatus())) {
            TimetableSearchCriteria searchCriteria = new TimetableSearchCriteria();
            searchCriteria.setStatus(TimetableStatus.APPROVED);
            List<ResourceTimetable> timetables = timetableService.getByCriteria(searchCriteria);
            for (ResourceTimetable resourceTimetable : timetables) {
                if (!resourceTimetable.getId().equals(timetable.getDescription())) {
                    if (DateUtils.beforeOrEquals(timetable.getStartDate(), resourceTimetable.getEndDate()) &&
                        DateUtils.afterOrEquals(timetable.getEndDate(), resourceTimetable.getStartDate())) {
                        addMessage(TIMETABLE_TIME_OVERLAPPED);
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
