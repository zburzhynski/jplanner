package com.zburzhynski.jplanner.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jplanner.impl.rest.domain.PatientResponse;
import com.zburzhynski.jplanner.impl.rest.domain.PatientSearchRequest;
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
    public PatientResponse getByCriteria(PatientSearchRequest request) {
        WebResource webResource = client.resource("http://localhost:8080/jdent/rest/patient/get-by-criteria");
        return webResource.accept(MediaType.APPLICATION_XML).post(PatientResponse.class, request);
    }

}
