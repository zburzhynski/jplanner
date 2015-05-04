package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Position.P_NAME;
import com.zburzhynski.jplanner.api.repository.IPositionRepository;
import com.zburzhynski.jplanner.impl.domain.Position;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of {@link PositionRepository} interface.
 * <p/>
 * Date: 04.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Repository("positionRepository")
public class PositionRepository extends AbstractBaseRepository<String, Position>
    implements IPositionRepository<String, Position> {

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
