package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Timetable remove validator.
 * <p/>
 * Date: 16.11.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class TimetableRemoveValidator extends BaseValidator {

    private static final String TIMETABLE_HAS_SCHEDULES = "timetableRemoveValidator.timetableHasSchedules";

    @Autowired
    private IResourceTimetableService resourceTimetableService;

    /**
     * Validates resource timetable.
     *
     * @param timetable {@link ResourceTimetable}
     * @return true if valid, else false
     */
    public boolean validate(ResourceTimetable timetable) {
        if (resourceTimetableService.hasSchedules(timetable)) {
            addMessage(TIMETABLE_HAS_SCHEDULES);
            return false;
        }
        return true;
    }

}
