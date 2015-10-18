package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.AMPERSAND;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.EQUAL;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.api.service.ITimetableService;
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
import javax.faces.context.FacesContext;

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

    private static final String TIMETABLES_COMPONENT = "timetablesForm:timetables";
    private static final String RESOURCE_ID_PARAM = "resourceId";
    private static final String TIMETABLE_ID_PARAM = "timetableId";

    private String resourceId;

    private AvailableResource resource;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    @ManagedProperty(value = "#{timetableService}")
    private ITimetableService timetableService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        String resourceIdParam = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get(RESOURCE_ID_PARAM);
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
        return View.TIMETABLE_TEMPLATE.getPath() + AMPERSAND + RESOURCE_ID_PARAM + EQUAL + resourceId;
    }

    /**
     * Edits timetable.
     *
     * @param editedTimetable edited timetable
     * @return path for navigating
     */
    public String editTimetable(Timetable editedTimetable) {
        return View.TIMETABLE.getPath() + AMPERSAND + TIMETABLE_ID_PARAM + EQUAL + editedTimetable.getId();
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

    public void setTimetableService(ITimetableService timetableService) {
        this.timetableService = timetableService;
    }

}
