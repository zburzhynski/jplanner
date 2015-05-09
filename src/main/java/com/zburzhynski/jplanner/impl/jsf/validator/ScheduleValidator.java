package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    /**
     * Validates schedule event.
     *
     * @param schedule {@Schedule}
     * @return true if valid, else false
     */
    public boolean validate(Schedule schedule) {
        Set<Boolean> result = new HashSet<>();
        result.add(checkRequiredFields(schedule));
        result.add(checkPeriod(schedule));
        return !result.contains(false);
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

}
