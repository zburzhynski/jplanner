package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Search dental visit response.
 * <p/>
 * Date: 17.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class SearchVisitResponse implements Serializable {

    private List<VisitDto> visits = new ArrayList<>();

    private int totalCount;

    /**
     * Adds dental visit.
     *
     * @param visit dental visit to add
     */
    public void addVisit(VisitDto visit) {
        visits.add(visit);
    }

    public List<VisitDto> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitDto> visits) {
        this.visits = visits;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
