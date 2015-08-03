package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.Workplace.P_CABINET;
import com.zburzhynski.jplanner.api.repository.IWorkplaceRepository;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IWorkplaceRepository} interface.
 * <p/>
 * Date: 03.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository
public class WorkplaceRepository extends AbstractBaseRepository<String, Workplace>
    implements IWorkplaceRepository<Workplace> {

    @Override
    public List<Workplace> getAll() {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_CABINET, P_CABINET);
        criteria.addOrder(Order.asc(P_CABINET + DOT + Cabinet.P_NUMBER));
        criteria.addOrder(Order.asc(Workplace.P_NAME));
        return criteria.list();
    }

    @Override
    protected Class<? extends Workplace> getDomainClass() {
        return Workplace.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return null;
    }

}
