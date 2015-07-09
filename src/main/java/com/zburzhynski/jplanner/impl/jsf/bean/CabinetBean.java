package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.impl.criteria.CabinetSearchCriteria;
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

    private static final int CABINET_PAGE_COUNT = 15;

    private LazyDataModel<Cabinet> cabinetModel;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

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

    public LazyDataModel<Cabinet> getCabinetModel() {
        return cabinetModel;
    }

    public Integer getRowCount() {
        return CABINET_PAGE_COUNT;
    }

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

}
