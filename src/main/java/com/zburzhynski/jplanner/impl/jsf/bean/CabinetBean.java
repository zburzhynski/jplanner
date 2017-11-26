package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.ModificationMode.CREATE;
import static com.zburzhynski.jplanner.api.domain.ModificationMode.EDIT;
import static com.zburzhynski.jplanner.api.domain.View.CABINET;
import static com.zburzhynski.jplanner.api.domain.View.CABINETS;
import static com.zburzhynski.jplanner.api.domain.View.WORKPLACE;
import com.zburzhynski.jplanner.api.criteria.CabinetSearchCriteria;
import com.zburzhynski.jplanner.api.domain.ModificationMode;
import com.zburzhynski.jplanner.api.exception.LinkedWorkplaceExistException;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.util.MessageHelper;
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

    public static final String LINKED_WORKPLACE_EXIST = "cabinet.linkedWorkplaceExist";

    private Cabinet cabinet;

    private Workplace workplace;

    private LazyDataModel<Cabinet> cabinetModel;

    private ModificationMode workplaceModificationMode;

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{messageHelper}")
    private MessageHelper messageHelper;

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
        return CABINET.getPath();
    }

    /**
     * Edits cabinet.
     *
     * @param cabinetId cabinet id
     * @return path for navigating
     */
    public String editCabinet(String cabinetId) {
        cabinet = (Cabinet) cabinetService.getById(cabinetId);
        return CABINET.getPath();
    }

    /**
     * Saves cabinet.
     *
     * @return path for navigating
     */
    public String saveCabinet() {
        cabinetService.saveOrUpdate(cabinet);
        return CABINETS.getPath();
    }

    /**
     * Removes cabinet.
     *
     * @return path for navigating
     */
    public String removeCabinet() {
        try {
            cabinetService.delete(cabinet);
        } catch (LinkedWorkplaceExistException e) {
            messageHelper.addMessage(LINKED_WORKPLACE_EXIST);
            return null;
        }
        return CABINETS.getPath();
    }


    /**
     * Adds workplace.
     *
     * @return path for navigating
     */
    public String addWorkplace() {
        workplaceModificationMode = CREATE;
        workplace = new Workplace();
        return WORKPLACE.getPath();
    }

    /**
     * Edits workplace.
     *
     * @return path for navigating
     */
    public String editWorkplace() {
        workplaceModificationMode = EDIT;
        return WORKPLACE.getPath();
    }

    /**
     * Saves workplace.
     *
     * @return path for navigating
     */
    public String saveWorkplace() {
        if (CREATE.equals(workplaceModificationMode)) {
            cabinet.addWorkplace(workplace);
        }
        return CABINET.getPath();
    }

    /**
     * Removes workplace.
     *
     * @return path for navigating
     */
    public String removeWorkplace() {
        cabinet.removeWorkplace(workplace);
        return CABINET.getPath();
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
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

    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

}
