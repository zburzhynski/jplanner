package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.impl.rest.client.IEmployeeRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.SearchEmployeeResponse;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Employee bean.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class EmployeeBean implements Serializable {

    @ManagedProperty(value = "#{employeeRestClient}")
    private IEmployeeRestClient employeeRestClient;

    /**
     * Refresh employee reference.
     */
    public void refresh() {
        SearchEmployeeResponse response = employeeRestClient.getAll();
    }

    public void setEmployeeRestClient(IEmployeeRestClient employeeRestClient) {
        this.employeeRestClient = employeeRestClient;
    }

}
