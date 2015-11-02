package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
    protected Class<? extends Quota> getDomainClass() {
        return Quota.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
