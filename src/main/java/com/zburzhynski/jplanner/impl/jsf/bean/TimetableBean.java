package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Timetable bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TimetableBean implements Serializable {

    public static final String RESOURCE_ID_PARAM = "resourceId";
    public static final String TIMETABLE_ID_PARAM = "timetableId";
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
        String resourceIdParam = JsfUtils.getRequestParam(RESOURCE_ID_PARAM);
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
        Map<String, Object> params = new HashMap<>();
        params.put(RESOURCE_ID_PARAM, resourceId);
        return JsfUtils.buildUrl(View.TIMETABLE_TEMPLATE.getPath(), params);
    }

    /**
     * Edits timetable.
     *
     * @param editedTimetable edited timetable
     * @return path for navigating
     */
    public String editTimetable(Timetable editedTimetable) {
        Map<String, Object> params = new HashMap<>();
        params.put(TIMETABLE_ID_PARAM, editedTimetable.getId());
        return JsfUtils.buildUrl(View.TIMETABLE.getPath(), params);
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
