package com.zburzhynski.jplanner.impl.rest.facade;

import com.zburzhynski.jplanner.impl.rest.domain.UpdateScheduleRequest;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleNotFoundException;

/**
 * Schedule facade interface.
 * <p/>
 * Date: 05.12.2017
 *
 * @author Nikita Shevtsov
 */
public interface IScheduleFacade {

    /**
     * Update schedule.
     *
     * @param request {@link UpdateScheduleRequest}
     * @throws ScheduleNotFoundException if any
     */
    void updateSchedule(UpdateScheduleRequest request) throws ScheduleNotFoundException;

}
