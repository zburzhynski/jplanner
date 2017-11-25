package com.zburzhynski.jplanner.api.dto.response;

import com.zburzhynski.jplanner.impl.domain.Quota;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Create quota response.
 * <p/>
 * Date: 20.11.2017
 *
 * @author Nikita Shevtsov
 */
public class CreateQuotaResponse implements Serializable {

    private Set<Quota> uncreatedQuotas;

    /**
     * Adds uncreated quota.
     *
     * @param quota uncreated quota to add
     */
    public void addUncreatedQuota(Quota quota) {
        getUncreatedQuotas().add(quota);
    }

    /**
     * Gets uncreated quotas.
     *
     * @return uncreated quotas
     */
    public Set<Quota> getUncreatedQuotas() {
        if (uncreatedQuotas == null) {
            uncreatedQuotas = new HashSet<>();
        }
        return uncreatedQuotas;
    }

    public void setUncreatedQuotas(Set<Quota> uncreatedQuotas) {
        this.uncreatedQuotas = uncreatedQuotas;
    }

}
