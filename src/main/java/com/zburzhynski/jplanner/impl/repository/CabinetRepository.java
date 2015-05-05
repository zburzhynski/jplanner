package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Cabinet.P_NAME;

import com.zburzhynski.jplanner.api.repository.ICabinetRepository;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
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