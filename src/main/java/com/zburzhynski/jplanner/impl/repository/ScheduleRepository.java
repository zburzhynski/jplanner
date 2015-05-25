package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Person.P_ID;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_END_DATE;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_PATIENT_ID;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_PERSON;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_START_DATE;
import static com.zburzhynski.jplanner.impl.domain.Schedule.P_WORKPLACE;
import com.zburzhynski.jplanner.api.repository.IScheduleRepository;
import com.zburzhynski.jplanner.impl.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
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
        criteria.createAlias(P_WORKPLACE, P_WORKPLACE);
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
        if (searchCriteria.getWorkplace() != null) {
            criteria.add(Restrictions.eq(P_WORKPLACE, searchCriteria.getWorkplace()));
        }
        if (searchCriteria.getDoctor() != null) {
            criteria.add(Restrictions.eq(P_DOCTOR, searchCriteria.getDoctor()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getPatientId())) {
            criteria.add(Restrictions.eq(P_PATIENT_ID, searchCriteria.getPatientId()));
        }
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Schedule> containByCriteria(ScheduleSearchCriteria containCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        if (containCriteria.getStartDate() != null && containCriteria.getEndDate() != null) {
            Conjunction first = Restrictions.conjunction();
            first.add(Restrictions.gt(P_START_DATE, containCriteria.getStartDate()));
            first.add(Restrictions.lt(P_START_DATE, containCriteria.getEndDate()));
            Conjunction second = Restrictions.conjunction();
            second.add(Restrictions.gt(P_END_DATE, containCriteria.getStartDate()));
            second.add(Restrictions.lt(P_END_DATE, containCriteria.getEndDate()));
            Conjunction third = Restrictions.conjunction();
            third.add(Restrictions.le(P_START_DATE, containCriteria.getStartDate()));
            third.add(Restrictions.ge(P_END_DATE, containCriteria.getEndDate()));
            Disjunction rangeCondition = Restrictions.disjunction();
            rangeCondition.add(first);
            rangeCondition.add(second);
            rangeCondition.add(third);
            criteria.add(rangeCondition);
        }
        if (containCriteria.getWorkplace() != null) {
            criteria.add(Restrictions.eq(P_WORKPLACE, containCriteria.getWorkplace()));
        }
        if (containCriteria.getDoctor() != null) {
            criteria.add(Restrictions.eq(P_DOCTOR, containCriteria.getDoctor()));
        }
        if (StringUtils.isNotBlank(containCriteria.getPatientId())) {
            criteria.add(Restrictions.eq(P_PATIENT_ID, containCriteria.getPatientId()));
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
