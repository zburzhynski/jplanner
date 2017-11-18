package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_ASSISTANT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_ID;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_NAME;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLE;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLES;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_WORKPLACE;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTA;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTAS;
import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;
import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IAvailableResourceRepository;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.util.CriteriaHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

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
        criteria.createAlias(P_WORKPLACE, P_WORKPLACE);
        criteria.createAlias(P_ASSISTANT, P_ASSISTANT, LEFT_OUTER_JOIN);
        criteria.createAlias(P_TIMETABLES, P_TIMETABLE, LEFT_OUTER_JOIN);
        criteria.createAlias(P_TIMETABLE + DOT + P_QUOTAS, P_QUOTA, LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (AvailableResource) criteria.uniqueResult();
    }

    @Override
    public List<AvailableResource> findByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        Criteria criteria = buildSearchCriteria(searchCriteria);
        CriteriaHelper.addPagination(criteria, searchCriteria.getStart(), searchCriteria.getEnd());
        criteria.addOrder(Order.asc(P_NAME));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public int countByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        Criteria criteria = buildSearchCriteria(searchCriteria);
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
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

    private Criteria buildSearchCriteria(AvailableResourceSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_DOCTOR, P_DOCTOR);
        criteria.createAlias(P_WORKPLACE, P_WORKPLACE);
        criteria.createAlias(P_ASSISTANT, P_ASSISTANT, LEFT_OUTER_JOIN);
        criteria.createAlias(P_TIMETABLES, P_TIMETABLE, LEFT_OUTER_JOIN);
        criteria.createAlias(P_TIMETABLE + DOT + P_QUOTAS, P_QUOTA, LEFT_OUTER_JOIN);
        if (searchCriteria.getWorkplace() != null) {
            criteria.add(Restrictions.eq(P_WORKPLACE, searchCriteria.getWorkplace()));
        }
        if (searchCriteria.getDoctor() != null) {
            criteria.add(Restrictions.eq(P_DOCTOR, searchCriteria.getDoctor()));
        }
        if (searchCriteria.getAssistant() != null) {
            criteria.add(Restrictions.eq(P_ASSISTANT, searchCriteria.getAssistant()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getQuotaIds())) {
            criteria.add(Restrictions.in(P_QUOTA + DOT + P_ID, searchCriteria.getQuotaIds()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getExcludedIds())) {
            criteria.add(Restrictions.not(Restrictions.in(P_ID, searchCriteria.getExcludedIds())));
        }
        return criteria;
    }

}
