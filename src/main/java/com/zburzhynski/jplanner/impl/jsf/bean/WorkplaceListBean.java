package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.IWorkplaceService;
import com.zburzhynski.jplanner.impl.domain.Workplace;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

/**
 * Workplace list bean.
 * <p/>
 * Date: 03.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class WorkplaceListBean implements Serializable {

    private List<Workplace> workplaces;

    @ManagedProperty(value = "#{workplaceService}")
    private IWorkplaceService workplaceService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        workplaces = workplaceService.getAll();
    }

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaceService(IWorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

}
