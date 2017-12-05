package com.zburzhynski.jplanner.impl.rest.service;

import com.zburzhynski.jplanner.impl.rest.domain.UpdatePatientRequest;
import com.zburzhynski.jplanner.impl.rest.facade.IScheduleFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Schedule rest service.
 * <p/>
 * Date: 04.12.2017
 *
 * @author Nikita Shevtsov
 */
@Component
@Path("/schedule")
public class ScheduleRestService {

    @Autowired
    private IScheduleFacade scheduleFacade;

    /**
     * Updates schedule patient id.
     *
     * @param request {@link UpdatePatientRequest} request
     */
    @POST
    @Path("/update-patient-id")
    @Produces(MediaType.APPLICATION_XML)
    public void updatePatientId(UpdatePatientRequest request) {
        scheduleFacade.updatePatientId(request);
    }

}
