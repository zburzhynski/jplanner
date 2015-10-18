package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Available resource list bean.
 * <p/>
 * Date: 17.10.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class ResourceListBean implements Serializable {

    private List<AvailableResource> resources;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        resources = resourceService.getAll();
    }

    public List<AvailableResource> getResources() {
        return resources;
    }

    public void setResourceService(IAvailableResourceService resourceService) {
        this.resourceService = resourceService;
    }

}
