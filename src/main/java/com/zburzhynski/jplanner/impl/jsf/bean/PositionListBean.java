package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.domain.Position;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Position list bean.
 * <p/>
 * Date: 07.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class PositionListBean {

    private List<Position> positions;

    @ManagedProperty(value = "#{positionService}")
    private IPositionService positionService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        positions = positionService.getAll();
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositionService(IPositionService positionService) {
        this.positionService = positionService;
    }

}
