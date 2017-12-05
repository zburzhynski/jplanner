package com.zburzhynski.jplanner.impl.rest.facade;

import com.zburzhynski.jplanner.impl.rest.domain.UpdatePatientRequest;

/**
 * Schedule facade interface.
 * <p/>
 * Date: 05.12.2017
 *
 * @author Nikita Shevtsov
 */
public interface IScheduleFacade {

    /**
     * Update schedule patient id.
     *
     * @param request {@link UpdatePatientRequest}
     */
    void updatePatientId(UpdatePatientRequest request);

}
