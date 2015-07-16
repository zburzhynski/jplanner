package com.zburzhynski.jplanner.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.ErrorResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.JdentUnavailableException;
import com.zburzhynski.jplanner.impl.rest.exception.PatientNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleEventAlreadyExistException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Implementation of {@link IVisitRestClient} interface.
 * <p/>
 * Date: 16.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class VisitRestClient implements IVisitRestClient {

    private static final String CREATE_VISIT_URL = "rest/visit/create-visit";

    private Client client = Client.create(new DefaultClientConfig());

    @Override
    public CreateVisitResponse createVisit(CreateVisitRequest request, String jdentUrl)
        throws PatientNotFoundException, EmployeeNotFoundException,
        ScheduleEventAlreadyExistException, JdentUnavailableException {
        try {
            WebResource webResource = client.resource(jdentUrl + CREATE_VISIT_URL);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, request);
            if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                return response.getEntity(CreateVisitResponse.class);
            } else {
                ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
                com.zburzhynski.jplanner.api.domain.Error.throwException(errorResponse.getErrorId());
                return null;
            }
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            throw new JdentUnavailableException("Jdent service not available");
        }
    }

}
