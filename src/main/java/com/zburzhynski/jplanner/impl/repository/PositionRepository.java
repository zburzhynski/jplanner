package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Position.P_NAME;
import com.zburzhynski.jplanner.api.criteria.PositionSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IPositionRepository;
import com.zburzhynski.jplanner.impl.domain.Position;
import com.zburzhynski.jplanner.impl.util.CriteriaHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IPositionRepository} interface.
 * <p/>
 * Date: 04.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Repository("positionRepository")
public class PositionRepository extends AbstractBaseRepository<String, Position>
    implements IPositionRepository<String, Position> {

    @Override
    public List<Position> findByCriteria(PositionSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        CriteriaHelper.addPagination(criteria, searchCriteria.getStart(), searchCriteria.getEnd());
        criteria.addOrder(Order.asc(P_NAME));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public int countByCriteria(PositionSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    @Override
    public boolean isUsed(Position position) {
        return false;
    }

    @Override
    protected Class<? extends Position> getDomainClass() {
        return Position.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        Map<String, Boolean> orders = new LinkedHashMap<>();
        orders.put(P_NAME, true);
        return orders;
    }

}
