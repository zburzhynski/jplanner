package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.Cabinet.P_NAME;
import static com.zburzhynski.jplanner.impl.domain.Cabinet.P_NUMBER;
import static com.zburzhynski.jplanner.impl.domain.Cabinet.P_WORKPLACE;
import static com.zburzhynski.jplanner.impl.domain.Cabinet.P_WORKPLACES;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static org.hibernate.sql.JoinType.LEFT_OUTER_JOIN;
import com.zburzhynski.jplanner.api.criteria.CabinetSearchCriteria;
import com.zburzhynski.jplanner.api.repository.ICabinetRepository;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.util.CriteriaHelper;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ICabinetRepository} interface.
 * <p/>
 * Date: 05.05.2015
 *
 * @author Mikalai Karabeika
 */
@Repository("cabinetRepository")
public class CabinetRepository extends AbstractBaseRepository<String, Cabinet>
    implements ICabinetRepository<String, Cabinet> {

    @Override
    public Cabinet findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_WORKPLACES, P_WORKPLACE, LEFT_OUTER_JOIN);
        criteria.setFetchMode(P_WORKPLACES, FetchMode.JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        criteria.addOrder(Order.asc(P_WORKPLACE + DOT + Workplace.P_NAME));
        return (Cabinet) criteria.uniqueResult();
    }

    @Override
    public List<Cabinet> findByCriteria(CabinetSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        CriteriaHelper.addPagination(criteria, searchCriteria.getStart(), searchCriteria.getEnd());
        criteria.addOrder(Order.asc(P_NUMBER));
        criteria.addOrder(Order.asc(P_NAME));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public int countByCriteria(CabinetSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    @Override
    public List<Cabinet> findAll() {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_WORKPLACES, P_WORKPLACE, LEFT_OUTER_JOIN);
        criteria.setFetchMode(P_WORKPLACES, FetchMode.JOIN);
        criteria.addOrder(Order.asc(P_NUMBER));
        criteria.addOrder(Order.asc(P_NAME));
        criteria.addOrder(Order.asc(P_WORKPLACE + DOT + Workplace.P_NAME));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public boolean isUsed(Cabinet cabinet) {
        return false;
    }

    @Override
    protected Class<? extends Cabinet> getDomainClass() {
        return Cabinet.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        Map<String, Boolean> orders = new LinkedHashMap<>();
        orders.put(P_NAME, true);
        return orders;
    }

}