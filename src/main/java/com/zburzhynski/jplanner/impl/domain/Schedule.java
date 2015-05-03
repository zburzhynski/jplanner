package com.zburzhynski.jplanner.impl.domain;

import com.zburzhynski.jplanner.api.domain.ScheduleStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.primefaces.model.ScheduleEvent;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    public static final String P_PERSON = "person";
    public static final String P_STATUS = "status";
    public static final String P_DOCTOR = "doctor";
    public static final String P_START_DATE = "startDate";
    public static final String P_END_DATE = "endDate";

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person = new Person();

    @Column(name = "schedule_status")
    @Enumerated(value = EnumType.STRING)
    private ScheduleStatus status = ScheduleStatus.PLANNED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Employee doctor = new Employee();

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "title")
    private String title;

    @Column(name = "complaint")
    private String complaint;

    @Column(name = "additional_info")
    private String additionalInfo;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
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

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String getDescription() {
        return title;
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
            .append(person, that.person)
            .append(status, that.status)
            .append(doctor, that.doctor)
            .append(startDate, that.startDate)
            .append(endDate, that.endDate)
            .append(title, that.title)
            .append(complaint, that.complaint)
            .append(additionalInfo, that.additionalInfo)
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
            .append(person)
            .append(status)
            .append(doctor)
            .append(startDate)
            .append(endDate)
            .append(title)
            .append(complaint)
            .append(additionalInfo)
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
            .append("person", person)
            .append("status", status)
            .append("doctor", doctor)
            .append("startDate", startDate)
            .append("endDate", endDate)
            .append("title", title)
            .append("complaint", complaint)
            .append("additionalInfo", additionalInfo)
            .append("data", data)
            .append("allDay", allDay)
            .append("styleClass", styleClass)
            .append("editable", editable)
            .toString();
    }

}
