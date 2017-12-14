package com.zburzhynski.jplanner.impl.jsf.bean;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jplanner.api.criteria.ScheduleSearchCriteria;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import com.zburzhynski.jplanner.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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

    private static final String TIMETABLE_HAS_SCHEDULES = "timetableRemoveValidator.timetableHasSchedules";
    public static final String RESOURCE_ID_PARAM = "resourceId";
    public static final String TIMETABLE_ID_PARAM = "timetableId";
    public static final String TIMETABLE_PARAM = "timetable";
    public static final String RESOURCE_PARAM = "resource";
    private static final String TIMETABLES_COMPONENT = "timetablesForm:timetables";

    private String resourceId;

    private AvailableResource resource;

    @ManagedProperty(value = "#{availableResourceService}")
    private IAvailableResourceService resourceService;

    @ManagedProperty(value = "#{scheduleService}")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

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
    public String editTimetable(ResourceTimetable editedTimetable) {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        JsfUtils.setFlashAttribute(TIMETABLE_PARAM, editedTimetable);
        return View.TIMETABLE.getPath();
    }

    /**
     * Removes timetable.
     *
     * @param removedTimetable timetable to remove
     */
    public void removeTimetable(ResourceTimetable removedTimetable) {
        ScheduleSearchCriteria searchCriteria = new ScheduleSearchCriteria();
        searchCriteria.setStartDate(removedTimetable.getStartDate());
        searchCriteria.setEndDate(removedTimetable.getEndDate());
        searchCriteria.setDoctor(removedTimetable.getAvailableResource().getDoctor());
        searchCriteria.setWorkplace(removedTimetable.getAvailableResource().getWorkplace());
        if (scheduleService.countByCriteria(searchCriteria) > 0) {
            addMessage(TIMETABLE_HAS_SCHEDULES);
            return;
        }
        resource.removeTimetable(removedTimetable);
        resourceService.saveOrUpdate(resource);
    }

    /**
     * Adds timetable quotas.
     *
     * @param timetable timetable to add quotas
     * @return path for navigating
     */
    public String addQuota(ResourceTimetable timetable) {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        JsfUtils.setFlashAttribute(TIMETABLE_ID_PARAM, timetable.getId());
        return View.TIMETABLE_TEMPLATE.getPath();
    }

    /**
     * Edit timetable quotas.
     *
     * @param timetable timetable to edit quotas
     * @return path for navigating
     */
    public String editQuota(ResourceTimetable timetable) {
        JsfUtils.setFlashAttribute(RESOURCE_ID_PARAM, resourceId);
        JsfUtils.setFlashAttribute(TIMETABLE_ID_PARAM, timetable.getId());
        TimetableQuotaBean timetableBean = JsfUtils.getSessionBean("timetableQuotaBean");
        if (timetableBean != null) {
            timetableBean.init();
        }
        return View.TIMETABLE_QUOTAS.getPath();
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

    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    private void addMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(propertyReader.readProperty(message), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

}
