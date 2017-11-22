package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLE;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_WORKPLACE;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_AVAILABLE_RESOURCE;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_TIMETABLE_STATUS;
import com.zburzhynski.jplanner.api.criteria.QuotaSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IQuotaRepository} interface.
 * <p/>
 * Date: 02.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository("quotaRepository")
public class QuotaRepository extends AbstractBaseRepository<String, Quota>
    implements IQuotaRepository<String, Quota> {

    @Override
    public List<Quota> findByCriteria(QuotaSearchCriteria searchCriteria) {
        Criteria criteria = buildSearchCriteria(searchCriteria);
        criteria.addOrder(Order.asc(Quota.P_START_DATE));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public Integer countByCriteria(QuotaSearchCriteria searchCriteria) {
        Criteria criteria = buildSearchCriteria(searchCriteria);
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    @Override
    protected Class<? extends Quota> getDomainClass() {
        return Quota.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

    private Criteria buildSearchCriteria(QuotaSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_TIMETABLE, P_TIMETABLE);
        if (searchCriteria.isIntersectingPeriod()) {
            criteria.add(Restrictions.lt(Quota.P_START_DATE, searchCriteria.getEndDate()));
            criteria.add(Restrictions.gt(Quota.P_END_DATE, searchCriteria.getStartDate()));
        } else {
            criteria.add(Restrictions.ge(Quota.P_START_DATE, searchCriteria.getStartDate()));
            criteria.add(Restrictions.le(Quota.P_END_DATE, searchCriteria.getEndDate()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getDoctorId()) ||
            StringUtils.isNotBlank(searchCriteria.getWorkplaceId()) ||
            CollectionUtils.isNotEmpty(searchCriteria.getTimetableStatuses()) ||
            CollectionUtils.isNotEmpty(searchCriteria.getExcludedResourceIds())) {
            criteria.createAlias(P_TIMETABLE + DOT + P_AVAILABLE_RESOURCE, P_AVAILABLE_RESOURCE);
            criteria.createAlias(P_AVAILABLE_RESOURCE  + DOT + P_DOCTOR, P_DOCTOR);
            criteria.createAlias(P_AVAILABLE_RESOURCE  + DOT + P_WORKPLACE, P_WORKPLACE);
        }
        if (StringUtils.isNotBlank(searchCriteria.getDoctorId())) {
            criteria.add(Restrictions.eq(P_DOCTOR + DOT + P_ID, searchCriteria.getDoctorId()));
        }
        if (StringUtils.isNotBlank(searchCriteria.getWorkplaceId())) {
            criteria.add(Restrictions.eq(P_WORKPLACE + DOT + P_ID, searchCriteria.getWorkplaceId()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getTimetableStatuses())) {
            criteria.add(Restrictions.in(P_TIMETABLE + DOT + P_TIMETABLE_STATUS,
                searchCriteria.getTimetableStatuses()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getTypes())) {
            criteria.add(Restrictions.in(Quota.P_QUOTA_TYPE, searchCriteria.getTypes()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getExcludedResourceIds())) {
            criteria.add(Restrictions.not(Restrictions.in(P_AVAILABLE_RESOURCE + DOT +P_ID,
                searchCriteria.getExcludedResourceIds())));
        }
        return criteria;
    }

}
