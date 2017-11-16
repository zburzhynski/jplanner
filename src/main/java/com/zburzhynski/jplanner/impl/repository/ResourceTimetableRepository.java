package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTA;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTAS;
import com.zburzhynski.jplanner.api.repository.IResourceTimetableRepository;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link IResourceTimetableRepository} interface.
 * <p/>
 * Date: 06.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository("resourceTimetableRepository")
public class ResourceTimetableRepository extends AbstractBaseRepository<String, ResourceTimetable>
    implements IResourceTimetableRepository<String, ResourceTimetable> {

    @Override
    public ResourceTimetable findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_QUOTAS, P_QUOTA, JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (ResourceTimetable) criteria.uniqueResult();
    }

    @Override
    protected Class<? extends ResourceTimetable> getDomainClass() {
        return ResourceTimetable.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

    @Override
    public boolean hasSchedules(ResourceTimetable timetable) {
        for (Quota quota : timetable.getQuotas()) {
            Criteria criteria = getSession().createCriteria(Schedule.class);
            criteria.add(Restrictions.gt(Schedule.P_END_DATE, quota.getStartDate()));
            criteria.add(Restrictions.lt(Schedule.P_START_DATE, quota.getEndDate()));
            criteria.add(Restrictions.eq(Schedule.P_WORKPLACE + DOT + P_ID,
                timetable.getAvailableResource().getWorkplace().getId()));
            criteria.add(Restrictions.eq(Schedule.P_DOCTOR + DOT + P_ID,
                timetable.getAvailableResource().getDoctor().getId()));
            criteria.setProjection(Projections.rowCount());
            if (((Number) criteria.uniqueResult()).intValue() > 0) {
                return true;
            }
        }
        return false;
    }
}
