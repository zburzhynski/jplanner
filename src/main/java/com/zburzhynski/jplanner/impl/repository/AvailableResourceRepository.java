package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_ASSISTANT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_ID;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLE;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLES;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_WORKPLACE;
import static com.zburzhynski.jplanner.impl.domain.Timetable.P_QUOTA;
import static com.zburzhynski.jplanner.impl.domain.Timetable.P_QUOTAS;
import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;
import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IAvailableResourceRepository;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IAvailableResourceRepository} interface.
 * <p/>
 * Date: 17.10.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository("availableResourceRepository")
public class AvailableResourceRepository extends AbstractBaseRepository<String, AvailableResource>
    implements IAvailableResourceRepository<String, AvailableResource> {

    @Override
    public AvailableResource findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_DOCTOR, P_DOCTOR);
        criteria.createAlias(P_ASSISTANT, P_ASSISTANT);
        criteria.createAlias(P_WORKPLACE, P_WORKPLACE);
        criteria.createAlias(P_TIMETABLES, P_TIMETABLE, LEFT_OUTER_JOIN);
        criteria.createAlias(P_TIMETABLE + DOT + P_QUOTAS, P_QUOTA, LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (AvailableResource) criteria.uniqueResult();
    }

    @Override
    public List<AvailableResource> findByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        return new ArrayList<>();
    }

    @Override
    public int countByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        return 0;
    }

    @Override
    public boolean isUsed(AvailableResource resource) {
        return false;
    }

    @Override
    protected Class<? extends AvailableResource> getDomainClass() {
        return AvailableResource.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
