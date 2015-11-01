package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Timetables bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TimetablesBean implements Serializable {

    public static final String RESOURCE_ID_PARAM = "resourceId";
    public static final String TIMETABLE_ID_PARAM = "timetableId";
    public static final String TIMETABLE_PARAM = "timetable";
    public static final String RESOURCE_PARAM = "resource";
    private static final String TIMETABLES_COMPONENT = "timetablesForm:timetables";

    private String resourceId;

    private AvailableResource resource;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        String resourceIdParam = (String) JsfUtils.getFlashAttribute(RESOURCE_ID_PARAM);
        if (StringUtils.isBlank(resourceIdParam)) {
            List<AvailableResource> resources = resourceService.getAll();
            if (CollectionUtils.isNotEmpty(resources)) {
                resource = (AvailableResource) resourceService.getById(resources.get(0).getId());
            }
        } else {
            resource = (AvailableResource) resourceService.getById(resourceIdParam);
        }
        if (resource != null) {
            resourceId = resource.getId();
        }
    }

    /**
     * Adds timetable.
     *
     * @return path for navigating
     */
    public String addTimetable() {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        JsfUtils.setFlashAttribute(RESOURCE_PARAM, resource);
        return View.TIMETABLE.getPath();
    }

    /**
     * Edits timetable.
     *
     * @param editedTimetable edited timetable
     * @return path for navigating
     */
    public String editTimetable(Timetable editedTimetable) {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        JsfUtils.setFlashAttribute(TIMETABLE_PARAM, editedTimetable);
        return View.TIMETABLE.getPath();
    }

    /**
     * Removes timetable.
     *
     * @param removedTimetable timetable to remove
     */
    public void removeTimetable(Timetable removedTimetable) {
        resource.removeTimetable(removedTimetable);
        resourceService.saveOrUpdate(resource);
    }

    /**
     * Adds timetable quotas.
     *
     * @param timetable timetable to add quotas
     * @return path for navigating
     */
    public String addQuota(Timetable timetable) {
        JsfUtils.setFlashAttribute(TIMETABLE_ID_PARAM, timetable.getId());
        return View.TIMETABLE_TEMPLATE.getPath();
    }

    /**
     * Edit timetable quotas.
     *
     * @param timetable timetable to edit quotas
     * @return path for navigating
     */
    public String editQuota(Timetable timetable) {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        JsfUtils.setFlashAttribute(TIMETABLE_ID_PARAM, timetable.getId());
        return View.TIMETABLE_QUOTA.getPath();
    }

    /**
     * Employee select listener.
     */
    public void resourceSelectListener() {
        resource = (AvailableResource) resourceService.getById(resourceId);
        JsfUtils.update(TIMETABLES_COMPONENT);
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
