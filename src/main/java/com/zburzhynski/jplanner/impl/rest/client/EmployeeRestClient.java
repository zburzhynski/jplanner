package com.zburzhynski.jplanner.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jplanner.impl.rest.domain.SearchEmployeeResponse;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

/**
 * Implementation of {@link IEmployeeRestClient} interface.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class EmployeeRestClient implements IEmployeeRestClient {

    private static final String GET_ALL_URL = "rest/employee/get-all";

    private Client client = Client.create(new DefaultClientConfig());

    @Override
    public SearchEmployeeResponse getAll(String jdentUrl) {
        try {
            WebResource webResource = client.resource(jdentUrl + GET_ALL_URL);
            return webResource.accept(MediaType.APPLICATION_XML).post(SearchEmployeeResponse.class);
        } catch (Exception exception) {
            return new SearchEmployeeResponse();
        }
    }

}
