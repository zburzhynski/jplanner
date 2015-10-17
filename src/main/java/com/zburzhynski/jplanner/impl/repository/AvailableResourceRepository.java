package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IAvailableResourceRepository;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<AvailableResource> findByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        return new ArrayList<>();
    }

    @Override
    public int countByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        return 0;
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

}
