package com.zburzhynski.jplanner.impl.rest.client;

import com.zburzhynski.jplanner.impl.rest.domain.SearchEmployeeResponse;

/**
 * Employee rest client interface.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
public interface IEmployeeRestClient {

    /**
     * Gets all employees.
     *
     * @param jdentUrl jdent url
     * @return {@link SearchEmployeeResponse} search employee response
     */
    SearchEmployeeResponse getAll(String jdentUrl);

}
