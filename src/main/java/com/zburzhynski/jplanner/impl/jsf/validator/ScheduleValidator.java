package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Schedule event validator.
 * <p/>
 * Date: 08.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class ScheduleValidator extends BaseValidator {

    private static final String WORKPLACE_NOT_SELECTED = "scheduleValidator.workplaceNotSelected";
    private static final String START_DATE_GREATER_THEN_END_DATE = "scheduleValidator.startDateGreaterThenEndDate";
    private static final String TIME_NOT_AVAILABLE = "scheduleValidator.timeNotAvailable";
    private static final String DOCTOR_NOT_AVAILABLE = "scheduleValidator.doctorNotAvailable";
    private static final String PATIENT_NOT_AVAILABLE = "scheduleValidator.patientNotAvailable";

    @Autowired
    private IScheduleService scheduleService;

    /**
     * Validates schedule event.
     *
     * @param schedule {@link Schedule}
     * @return true if valid, else false
     */
    public boolean validate(Schedule schedule) {
        Set<Boolean> result = new HashSet<>();
        result.add(checkRequiredFields(schedule));
        result.add(checkPeriod(schedule));
        result.add(validateAvailability(schedule));
        return !result.contains(false);
    }

    /**
     * Validates time, patient and doctor of schedule event.
     *
     * @param schedule {@link Schedule}
     * @return true if valid, else false
     */
    public boolean validateAvailability(Schedule schedule) {
        if (checkIsTimeAvailable(schedule) && checkIsPatientAvailable(schedule)
            && checkIsDoctorAvailable(schedule)) {
            return true;
        }
        return false;
    }

    private boolean checkRequiredFields(Schedule schedule) {
        if (schedule.getWorkplace() == null) {
            addMessage(WORKPLACE_NOT_SELECTED);
            return false;
        }
        return true;
    }

    private boolean checkPeriod(Schedule schedule) {
        if (schedule.getStartDate().after(schedule.getEndDate())
            || schedule.getStartDate().equals(schedule.getEndDate())) {
            addMessage(START_DATE_GREATER_THEN_END_DATE);
            return false;
        }
        return true;
    }

    private boolean checkIsTimeAvailable(Schedule schedule) {
        ScheduleSearchCriteria containCriteria = new ScheduleSearchCriteria();
        containCriteria.setStartDate(schedule.getStartDate());
        containCriteria.setEndDate(schedule.getEndDate());
        containCriteria.setWorkplace(schedule.getWorkplace());
        List<Schedule> schedules = scheduleService.containByCriteria(containCriteria);
        if (CollectionUtils.isNotEmpty(schedules)) {
            for (Schedule event : schedules) {
                if (!StringUtils.equals(event.getId(), schedule.getId())) {
                    addMessage(TIME_NOT_AVAILABLE);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIsPatientAvailable(Schedule schedule) {
        if (StringUtils.isNotBlank(schedule.getPatientId())) {
            ScheduleSearchCriteria containCriteria = new ScheduleSearchCriteria();
            containCriteria.setStartDate(schedule.getStartDate());
            containCriteria.setEndDate(schedule.getEndDate());
            containCriteria.setPatientId(schedule.getPatientId());
            List<Schedule> schedules = scheduleService.containByCriteria(containCriteria);
            if (CollectionUtils.isNotEmpty(schedules)) {
                for (Schedule event : schedules) {
                    if (!StringUtils.equals(event.getId(), schedule.getId())) {
                        Schedule exist = (Schedule) scheduleService.getById(event.getId());
                        addMessage(PATIENT_NOT_AVAILABLE, exist.getPerson().getShortName(),
                            exist.getWorkplace().getName());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkIsDoctorAvailable(Schedule schedule) {
        ScheduleSearchCriteria containCriteria = new ScheduleSearchCriteria();
        containCriteria.setStartDate(schedule.getStartDate());
        containCriteria.setEndDate(schedule.getEndDate());
        containCriteria.setDoctor(schedule.getDoctor());
        List<Schedule> schedules = scheduleService.containByCriteria(containCriteria);
        if (CollectionUtils.isNotEmpty(schedules)) {
            for (Schedule event : schedules) {
                if (!StringUtils.equals(event.getId(), schedule.getId())) {
                    Schedule exist = (Schedule) scheduleService.getById(event.getId());
                    addMessage(DOCTOR_NOT_AVAILABLE, exist.getDoctor().getPerson().getShortName(),
                        exist.getPerson().getShortName(), exist.getWorkplace().getName());
                    return false;
                }
            }
        }
        return true;
    }

}
