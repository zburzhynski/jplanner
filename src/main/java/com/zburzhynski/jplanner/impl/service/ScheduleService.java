package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.repository.IScheduleRepository;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IScheduleService} interface.
 * <p/>
 * Date: 24.04.15
 *
 * @author Vladimir Zburzhynski
 */
@Service("scheduleService")
@Transactional(readOnly = true)
public class ScheduleService implements IScheduleService<String, Schedule> {

    @Autowired
    private IScheduleRepository scheduleRepository;

    @Override
    public Schedule getById(String id) {
        return (Schedule) scheduleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Schedule schedule) {
        boolean result = false;
        if (schedule != null) {
            if (StringUtils.isBlank(schedule.getId())) {
                scheduleRepository.insert(schedule);
                result = true;
            } else {
                scheduleRepository.update(schedule);
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Schedule schedule) {
        boolean deleted = false;
        if (schedule != null) {
            scheduleRepository.delete(schedule);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

}
