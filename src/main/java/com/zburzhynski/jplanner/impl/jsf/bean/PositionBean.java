package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.POSITION;
import static com.zburzhynski.jplanner.api.domain.View.POSITIONS;
import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.criteria.PositionSearchCriteria;
import com.zburzhynski.jplanner.impl.domain.Position;
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
 * Job position bean.
 * <p/>
 * Date: 04.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class PositionBean implements Serializable {

    private static final int PATIENT_PAGE_COUNT = 15;

    private Position position;

    private LazyDataModel<Position> positionModel;

    private Integer rowCount = PATIENT_PAGE_COUNT;

    @ManagedProperty(value = "#{positionService}")
    private IPositionService positionService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        positionModel = new LazyDataModel<Position>() {
            @Override
            public List<Position> load(int first, int pageSize, String sortField,
                                       SortOrder sortOrder, Map<String, Object> filters) {
                PositionSearchCriteria searchCriteria = new PositionSearchCriteria();
                searchCriteria.setStart(Long.valueOf(first));
                searchCriteria.setEnd(Long.valueOf(first + pageSize));
                List<Position> positions = positionService.getByCriteria(searchCriteria);
                int count = positionService.countByCriteria(searchCriteria);
                setRowCount(count);
                return positions;
            }
        };
    }

    /**
     * Adds job position.
     *
     * @return path for navigating
     */
    public String addPosition() {
        position = new Position();
        return POSITION.getPath();
    }

    /**
     * Edits job position.
     *
     * @return path for navigating
     */
    public String editPosition() {
        return POSITION.getPath();
    }

    /**
     * Saves job position.
     *
     * @return path for navigating
     */
    public String savePosition() {
        positionService.saveOrUpdate(position);
        return POSITIONS.getPath();
    }

    /**
     * Removes job position.
     *
     * @return path for navigating
     */
    public String removePosition() {
        positionService.delete(position);
        return POSITIONS.getPath();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LazyDataModel<Position> getPositionModel() {
        return positionModel;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setPositionService(IPositionService positionService) {
        this.positionService = positionService;
    }

}
