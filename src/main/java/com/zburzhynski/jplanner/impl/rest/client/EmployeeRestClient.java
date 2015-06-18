package com.zburzhynski.jplanner.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
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

    private ClientConfig config = new DefaultClientConfig();

    private Client client = Client.create(config);

    @Override
    public SearchEmployeeResponse getAll() {
        try {
            WebResource webResource = client.resource("http://localhost:8080/jdent/rest/employee/get-all");
            return webResource.accept(MediaType.APPLICATION_XML).post(SearchEmployeeResponse.class);
        } catch (Exception exception) {
            return new SearchEmployeeResponse();
        }
    }

}
