package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.repository.IScheduleRepository;
import com.zburzhynski.jplanner.impl.domain.Schedule;

import java.util.Map;

/**
 * Implementation of {@link IScheduleRepository} interface.
 * <p/>
 * Date: 23.04.15
 *
 * @author Vladimir Zburzhynski
 */
public class ScheduleRepository extends AbstractBaseRepository<String, Schedule> implements IScheduleRepository {

    @Override
    protected Class<? extends Schedule> getDomainClass() {
        return null;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return null;
    }

}
