package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_AVAILABLE_RESOURCE;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTA;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTAS;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_TIMETABLE_STATUS;
import com.zburzhynski.jplanner.api.criteria.TimetableSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IResourceTimetableRepository;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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
    public List<ResourceTimetable> findByCriteria(TimetableSearchCriteria searchCriteria) {
        Criteria criteria = buildSearchCriteria(searchCriteria);
        return criteria.list();
    }

    @Override
    public Integer countByCriteria(TimetableSearchCriteria searchCriteria) {
        Criteria criteria = buildSearchCriteria(searchCriteria);
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    @Override
    protected Class<? extends ResourceTimetable> getDomainClass() {
        return ResourceTimetable.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

    private Criteria buildSearchCriteria(TimetableSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_AVAILABLE_RESOURCE, P_AVAILABLE_RESOURCE);
        if (searchCriteria.isIntersectingPeriod()) {
            criteria.add(Restrictions.le(ResourceTimetable.P_START_DATE, searchCriteria.getEndDate()));
            criteria.add(Restrictions.ge(ResourceTimetable.P_END_DATE, searchCriteria.getStartDate()));
        } else {
            criteria.add(Restrictions.ge(ResourceTimetable.P_START_DATE, searchCriteria.getStartDate()));
            criteria.add(Restrictions.le(ResourceTimetable.P_END_DATE, searchCriteria.getEndDate()));
        }
        criteria.add(Restrictions.eq(P_AVAILABLE_RESOURCE + DOT + P_ID, searchCriteria.getResourceId()));
        if (searchCriteria.getStatus() != null) {
            criteria.add(Restrictions.eq(P_TIMETABLE_STATUS, searchCriteria.getStatus()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getExcludedIds())) {
            criteria.add(Restrictions.not(Restrictions.in(P_ID, searchCriteria.getExcludedIds())));
        }
        return criteria;
    }
}
