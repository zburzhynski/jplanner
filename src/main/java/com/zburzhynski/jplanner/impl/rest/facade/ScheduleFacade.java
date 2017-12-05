package com.zburzhynski.jplanner.impl.rest.facade;

import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import com.zburzhynski.jplanner.impl.rest.domain.UpdatePatientRequest;
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
    public void updatePatientId(UpdatePatientRequest request) {
        if (StringUtils.isBlank(request.getScheduleId())) {
            throw new IllegalArgumentException();
        }
        Schedule schedule = (Schedule) scheduleService.getById(request.getScheduleId());
        if (StringUtils.isNotBlank(request.getPatientId())) {
            schedule.getClient().setJdentPatientId(request.getPatientId());
        }
        scheduleService.saveOrUpdate(schedule);
    }

}
