package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    public List<Quota> findIntersecting(Date startDate, Date endDate) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.add(Restrictions.gt(Quota.P_END_DATE, startDate));
        criteria.add(Restrictions.lt(Quota.P_START_DATE, endDate));
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
