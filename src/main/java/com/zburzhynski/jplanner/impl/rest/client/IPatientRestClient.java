package com.zburzhynski.jplanner.impl.rest.client;

import com.zburzhynski.jplanner.impl.rest.domain.PatientResponse;
import com.zburzhynski.jplanner.impl.rest.domain.PatientSearchRequest;

/**
 * Patient rest client interface.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
public interface IPatientRestClient {

    /**
     * Gets patient by criteria.
     *
     * @param request {@link PatientSearchRequest} search request
     * @return {@link PatientResponse}
     */
    PatientResponse getByCriteria(PatientSearchRequest request);

}
