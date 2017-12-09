package com.zburzhynski.jplanner.impl.rest.service;

import static javax.ws.rs.core.Response.status;
import com.zburzhynski.jplanner.impl.rest.domain.ErrorResponse;
import com.zburzhynski.jplanner.impl.rest.domain.UpdateScheduleRequest;
import com.zburzhynski.jplanner.impl.rest.facade.IScheduleFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
     * Updates schedule.
     *
     * @param request {@link UpdateScheduleRequest} request
     * @return response
     */
    @POST
    @Path("/update-schedule")
    @Produces(MediaType.APPLICATION_XML)
    public Response updateSchedule(UpdateScheduleRequest request) {
        try {
            scheduleFacade.updateSchedule(request);
            return status(Response.Status.OK).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
            return status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

}
