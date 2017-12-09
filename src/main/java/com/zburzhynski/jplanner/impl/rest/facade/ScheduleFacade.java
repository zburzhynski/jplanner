package com.zburzhynski.jplanner.impl.rest.facade;

import com.zburzhynski.jplanner.api.domain.ScheduleStatus;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import com.zburzhynski.jplanner.impl.rest.domain.UpdateScheduleRequest;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Schedule facade.
 * <p/>
 * Date: 05.12.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class ScheduleFacade implements IScheduleFacade {

    @Autowired
    private IScheduleService scheduleService;

    @Override
    public void updateSchedule(UpdateScheduleRequest request) throws ScheduleNotFoundException {
        if (StringUtils.isBlank(request.getScheduleId())) {
            throw new IllegalArgumentException();
        }
        Schedule schedule = (Schedule) scheduleService.getById(request.getScheduleId());
        if (schedule == null) {
            throw new ScheduleNotFoundException();
        }
        if (StringUtils.isNotBlank(request.getPatientId())) {
            schedule.getClient().setJdentPatientId(request.getPatientId());
        }
        if (StringUtils.isNotBlank(request.getScheduleStatus())) {
            schedule.setStatus(ScheduleStatus.valueOf(request.getScheduleStatus()));
        }
        scheduleService.saveOrUpdate(schedule);
    }

}
