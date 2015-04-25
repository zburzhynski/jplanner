package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.primefaces.model.ScheduleEvent;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Schedule event.
 * <p/>
 * Date: 23.04.15
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(schema = "jplanner", name = "schedule")
public class Schedule extends Domain implements ScheduleEvent {

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Transient
    private Object data;

    @Transient
    private boolean allDay;

    @Transient
    private String styleClass;

    @Transient
    private boolean editable = true;

    /**
     * Default constructor.
     */
    public Schedule() {
    }

    /**
     * Constructor.
     *
     * @param startDate event start date
     * @param endDate   event end date
     * @param title     event title
     */
    public Schedule(Date startDate, Date endDate, String title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    @Override
    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Schedule)) {
            return false;
        }

        Schedule that = (Schedule) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(startDate, that.startDate)
            .append(endDate, that.endDate)
            .append(title, that.title)
            .append(description, that.description)
            .append(data, that.data)
            .append(allDay, that.allDay)
            .append(styleClass, that.styleClass)
            .append(editable, that.editable)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(startDate)
            .append(endDate)
            .append(title)
            .append(description)
            .append(data)
            .append(allDay)
            .append(styleClass)
            .append(editable)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("startDate", startDate)
            .append("endDate", endDate)
            .append("title", title)
            .append("description", description)
            .append("data", data)
            .append("allDay", allDay)
            .append("styleClass", styleClass)
            .append("editable", editable)
            .toString();
    }

}
