package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.TimetableSearchCriteria;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        //TODO: Add quota validation
        return checkPeriod(timetable) && checkSchedules(timetable);
    }

    private boolean checkPeriod(ResourceTimetable timetable) {
        TimetableSearchCriteria searchCriteria = new TimetableSearchCriteria();
        searchCriteria.setStartDate(timetable.getStartDate());
        searchCriteria.setEndDate(timetable.getEndDate());
        searchCriteria.setResourceId(timetable.getAvailableResource().getId());
        searchCriteria.setIntersectingPeriod(true);
        if (StringUtils.isNotEmpty(timetable.getId())) {
            searchCriteria.getExcludedIds().add(timetable.getId());
        }
        if (timetableService.countByCriteria(searchCriteria) > 0) {
            addMessage(TIMETABLE_TIME_OVERLAPPED);
            return false;
        }
        return true;
    }

    private boolean checkSchedules(ResourceTimetable timetable) {
        if (isStatusChanged(timetable)) {
            ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
            searchCriteria.setStartDate(timetable.getStartDate());
            searchCriteria.setEndDate(timetable.getEndDate());
            searchCriteria.setDoctor(timetable.getAvailableResource().getDoctor());
            searchCriteria.setWorkplace(timetable.getAvailableResource().getWorkplace());
            if (scheduleService.countByCriteria(searchCriteria) > 0) {
                addMessage(TIMETABLE_HAS_SCHEDULES);
                return false;
            }
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


}
