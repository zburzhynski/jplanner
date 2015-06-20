package com.zburzhynski.jplanner.impl.rest.client;

import static javax.ws.rs.core.Response.Status;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.ErrorResponse;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.PatientNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleEventAlreadyExistException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

/**
 * Implementation of {@link IPatientRestClient} interface.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class PatientRestClient implements IPatientRestClient {

    private ClientConfig config = new DefaultClientConfig();

    private Client client = Client.create(config);

    @Override
    public SearchPatientResponse getByCriteria(SearchPatientRequest request) {
        try {
            WebResource webResource = client.resource("http://localhost:8080/jdent/rest/patient/get-by-criteria");
            return webResource.accept(MediaType.APPLICATION_XML).post(SearchPatientResponse.class, request);
        } catch (ClientHandlerException exception) {
            return new SearchPatientResponse();
        }
    }

    @Override
    public CreateVisitResponse createVisit(CreateVisitRequest request)
        throws PatientNotFoundException, EmployeeNotFoundException, ScheduleEventAlreadyExistException {
        try {
            WebResource webResource = client.resource("http://localhost:8080/jdent/rest/patient/create-visit");
            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, request);
            if (Status.OK.getStatusCode() == response.getStatus()) {
                return response.getEntity(CreateVisitResponse.class);
            } else {
                ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
                if ("PatientNotFoundException".equals(errorResponse.getErrorId())) {
                    throw new PatientNotFoundException();
                } else if ("ScheduleEventAlreadyExistException".equals(errorResponse.getErrorId())) {
                    throw new ScheduleEventAlreadyExistException();
                } else if ("EmployeeNotFoundException".equals(errorResponse.getErrorId())) {
                    throw new EmployeeNotFoundException();
                }
                return null;
            }
        } catch (ClientHandlerException exception) {
            return null;
        }
    }

}
