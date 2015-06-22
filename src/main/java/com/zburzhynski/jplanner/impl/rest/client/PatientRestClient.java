package com.zburzhynski.jplanner.impl.rest.client;

import static javax.ws.rs.core.Response.Status;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jplanner.api.domain.Error;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.ErrorResponse;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.JdentUnavailableException;
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

    private static final String GET_BY_CRITERIA_URL = "rest/patient/get-by-criteria";

    private static final String CREATE_VISIT_URL = "rest/patient/create-visit";

    private Client client = Client.create(new DefaultClientConfig());

    @Override
    public SearchPatientResponse getByCriteria(SearchPatientRequest request, String jdentUrl) {
        try {
            WebResource webResource = client.resource(jdentUrl + GET_BY_CRITERIA_URL);
            return webResource.accept(MediaType.APPLICATION_XML).post(SearchPatientResponse.class, request);
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            return new SearchPatientResponse();
        }
    }

    @Override
    public CreateVisitResponse createVisit(CreateVisitRequest request, String jdentUrl)
        throws PatientNotFoundException, EmployeeNotFoundException,
        ScheduleEventAlreadyExistException, JdentUnavailableException {
        try {
            WebResource webResource = client.resource(jdentUrl + CREATE_VISIT_URL);
            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, request);
            if (Status.OK.getStatusCode() == response.getStatus()) {
                return response.getEntity(CreateVisitResponse.class);
            } else {
                ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
                Error.throwException(errorResponse.getErrorId());
                return null;
            }
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            throw new JdentUnavailableException("Jdent service not available");
        }
    }

}
