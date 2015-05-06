package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

/**
 * Cabinet list bean.
 * <p/>
 * Date: 05.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class CabinetListBean implements Serializable {

    private List<Cabinet> cabinets;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        cabinets = cabinetService.getAll();
    }

    public List<Cabinet> getCabinets() {
        return cabinets;
    }

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

}
