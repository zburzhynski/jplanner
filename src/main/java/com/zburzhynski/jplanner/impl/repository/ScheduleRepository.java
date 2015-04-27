package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.repository.IScheduleRepository;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IScheduleRepository} interface.
 * <p/>
 * Date: 23.04.15
 *
 * @author Vladimir Zburzhynski
 */
@Repository("scheduleRepository")
public class ScheduleRepository extends AbstractBaseRepository<String, Schedule>
    implements IScheduleRepository<String, Schedule> {

    @Override
    public List<Schedule> findByCriteria(ScheduleSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        if (searchCriteria.getStartDate() != null) {
            criteria.add(Restrictions.ge(Schedule.P_START_DATE, searchCriteria.getStartDate()));
        }
        if (searchCriteria.getEndDate() != null) {
            criteria.add(Restrictions.le(Schedule.P_END_DATE, searchCriteria.getEndDate()));
        }
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    protected Class<? extends Schedule> getDomainClass() {
        return Schedule.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return null;
    }

}
