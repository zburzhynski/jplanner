package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLE;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_AVAILABLE_RESOURCE;
import com.zburzhynski.jplanner.api.criteria.IntersectedQuotaSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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
    public List<Quota> findIntersecting(IntersectedQuotaSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.add(Restrictions.gt(Quota.P_END_DATE, searchCriteria.getStartDate()));
        criteria.add(Restrictions.lt(Quota.P_START_DATE, searchCriteria.getEndDate()));
        if (StringUtils.isNotBlank(searchCriteria.getDoctorId())) {
            criteria.createAlias(P_TIMETABLE, P_TIMETABLE);
            criteria.createAlias(P_TIMETABLE + DOT + P_AVAILABLE_RESOURCE, P_AVAILABLE_RESOURCE);
            criteria.createAlias(P_AVAILABLE_RESOURCE  + DOT + P_DOCTOR, P_DOCTOR);
            criteria.add(Restrictions.eq(P_DOCTOR + DOT + P_ID, searchCriteria.getDoctorId()));
        }
        if (CollectionUtils.isNotEmpty(searchCriteria.getTypes())) {
            criteria.add(Restrictions.in(Quota.P_QUOTA_TYPE, searchCriteria.getTypes()));
        }
        criteria.addOrder(Order.asc(Quota.P_START_DATE));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    protected Class<? extends Quota> getDomainClass() {
        return Quota.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
