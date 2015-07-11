package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.criteria.CabinetSearchCriteria;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Cabinet bean.
 * <p/>
 * Date: 09.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class CabinetBean implements Serializable {

    private Cabinet cabinet;

    private LazyDataModel<Cabinet> cabinetModel;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        cabinetModel = new LazyDataModel<Cabinet>() {
            @Override
            public List<Cabinet> load(int first, int pageSize, String sortField,
                                      SortOrder sortOrder, Map<String, Object> filters) {
                CabinetSearchCriteria searchCriteria = new CabinetSearchCriteria();
                searchCriteria.setStart(Long.valueOf(first));
                searchCriteria.setEnd(Long.valueOf(first + pageSize));
                List<Cabinet> cabinets = cabinetService.getByCriteria(searchCriteria);
                int count = cabinetService.countByCriteria(searchCriteria);
                setRowCount(count);
                return cabinets;
            }
        };
    }

    /**
     * Adds cabinet.
     *
     * @return path for navigating
     */
    public String addCabinet() {
        cabinet = new Cabinet();
        return View.CABINET.getPath();
    }

    /**
     * Edits cabinet.
     *
     * @return path for navigating
     */
    public String editCabinet() {
        return View.CABINET.getPath();
    }

    /**
     * Saves cabinet.
     *
     * @return path for navigating
     */
    public String saveCabinet() {
        cabinetService.saveOrUpdate(cabinet);
        return View.CABINETS.getPath();
    }

    /**
     * Removes cabinet.
     *
     * @return path for navigating
     */
    public String removeCabinet() {
        cabinetService.delete(cabinet);
        return View.CABINETS.getPath();
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public LazyDataModel<Cabinet> getCabinetModel() {
        return cabinetModel;
    }

    public Integer getRowCount() {
        return configBean.getCabinetsPerPageCount();
    }

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

}
