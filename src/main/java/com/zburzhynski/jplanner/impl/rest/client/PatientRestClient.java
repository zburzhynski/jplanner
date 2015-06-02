package com.zburzhynski.jplanner.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientResponse;
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
        } catch (Exception exception) {
            return new SearchPatientResponse();
        }
    }

    @Override
    public CreateVisitResponse createVisit(CreateVisitRequest request) {
        try {
            WebResource webResource = client.resource("http://localhost:8080/jdent/rest/patient/create-visit");
            return webResource.accept(MediaType.APPLICATION_XML).post(CreateVisitResponse.class, request);
        } catch (Exception exception) {
            return new CreateVisitResponse();
        }
    }

}
