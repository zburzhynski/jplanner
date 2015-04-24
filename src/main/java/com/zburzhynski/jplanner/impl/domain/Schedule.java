package com.zburzhynski.jplanner.impl.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Schedule event.
 * <p/>
 * Date: 23.04.15
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "schedule")
public class Schedule extends Domain {

    private Date startDate;

    private Date endDate;

    private String title;

    private String description;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
