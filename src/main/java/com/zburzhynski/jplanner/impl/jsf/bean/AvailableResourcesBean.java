package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.exception.AvailableResourceHasLinkedTimetablesException;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.MessageHelper;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Available resources bean.
 * <p/>
 * Date: 29.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class AvailableResourcesBean implements Serializable {

    public static final String AVAILABLE_RESOURCE_ID_PARAM = "availableResourceId";
    public static final String RESOURCE_HAS_LINKED_TIMETABLES = "availableResource.resourceHasLinkedTimetables";

    private LazyDataModel<AvailableResource> resourceModel;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    @ManagedProperty(value = "#{messageHelper}")
    private MessageHelper messageHelper;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        resourceModel = new LazyDataModel<AvailableResource>() {
            @Override
            public List<AvailableResource> load(int first, int pageSize, String sortField,
                                                SortOrder sortOrder, Map<String, Object> filters) {
                AvailableResourceSearchCriteria searchCriteria = new AvailableResourceSearchCriteria();
                searchCriteria.setStart(Long.valueOf(first));
                searchCriteria.setEnd(Long.valueOf(first + pageSize));
                List<AvailableResource> resources = resourceService.getByCriteria(searchCriteria);
                int count = resourceService.countByCriteria(searchCriteria);
                setRowCount(count);
                return resources;
            }
        };
    }

    /**
     * Adds available resource.
     *
     * @return path for navigating
     */
    public String addResource() {
        return View.AVAILABLE_RESOURCE.getPath();
    }

    /**
     * Edits available resource.
     *
     * @param editedResource edited available resource
     * @return path for navigating
     */
    public String editResource(AvailableResource editedResource) {
        JsfUtils.setFlashAttribute(AVAILABLE_RESOURCE_ID_PARAM, editedResource.getId());
        return View.AVAILABLE_RESOURCE.getPath();
    }

    /**
     * Removes available resource.
     *
     * @param removedResource removed available resource
     */
    public void removeResource(AvailableResource removedResource) {
        try {
            resourceService.delete(removedResource);
        } catch (AvailableResourceHasLinkedTimetablesException e) {
            messageHelper.addMessage(RESOURCE_HAS_LINKED_TIMETABLES);
        }
    }

    public LazyDataModel<AvailableResource> getResourceModel() {
        return resourceModel;
    }

    public Integer getRowCount() {
        return configBean.getAvailableResourcesPerPageCount();
    }

    public void setResourceService(IAvailableResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

}
