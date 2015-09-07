package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.Timetable.P_QUOTA;
import static com.zburzhynski.jplanner.impl.domain.Timetable.P_QUOTAS;
import com.zburzhynski.jplanner.api.repository.ITimetableRepository;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ITimetableRepository} interface.
 * <p/>
 * Date: 06.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository("timetableRepository")
public class TimetableRepository extends AbstractBaseRepository<String, Timetable>
    implements ITimetableRepository<String, Timetable> {

    @Override
    public Timetable findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_QUOTAS, P_QUOTA, JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (Timetable) criteria.uniqueResult();
    }

    @Override
    protected Class<? extends Timetable> getDomainClass() {
        return Timetable.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
