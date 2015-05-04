package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Person.P_ID;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_END_DATE;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_PERSON;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_START_DATE;
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
    public Schedule findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_PERSON, P_PERSON);
        criteria.createAlias(P_DOCTOR, P_DOCTOR);
        criteria.add(Restrictions.eq(P_ID, id));
        return (Schedule) criteria.uniqueResult();
    }

    @Override
    public List<Schedule> findByCriteria(ScheduleSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        if (searchCriteria.getStartDate() != null) {
            criteria.add(Restrictions.ge(P_START_DATE, searchCriteria.getStartDate()));
        }
        if (searchCriteria.getEndDate() != null) {
            criteria.add(Restrictions.le(P_END_DATE, searchCriteria.getEndDate()));
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
