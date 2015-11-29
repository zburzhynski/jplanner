package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.impl.jsf.bean.AvailableResourcesBean.AVAILABLE_RESOURCE_ID_PARAM;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Available resource bean.
 * <p/>
 * Date: 29.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class AvailableResourceBean implements Serializable {

    private AvailableResource resource;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        String resourceId = (String) JsfUtils.getFlashAttribute(AVAILABLE_RESOURCE_ID_PARAM);
        if (StringUtils.isNotBlank(resourceId)) {
            resource = (AvailableResource) resourceService.getById(resourceId);
        } else {
            resource = new AvailableResource();
        }
    }

    /**
     * Saves available resource.
     *
     * @return path for navigating
     */
    public String saveResource() {
        resourceService.saveOrUpdate(resource);
        return View.AVAILABLE_RESOURCES.getPath();
    }

    public AvailableResource getResource() {
        return resource;
    }

    public void setResource(AvailableResource resource) {
        this.resource = resource;
    }

    public void setResourceService(IAvailableResourceService resourceService) {
        this.resourceService = resourceService;
    }

}
