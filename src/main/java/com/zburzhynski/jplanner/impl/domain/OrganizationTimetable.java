package com.zburzhynski.jplanner.impl.domain;

import com.zburzhynski.jplanner.api.domain.DayOfWeek;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Organization timetable.
 * <p/>
 * Date: 27.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "organization_timetable")
public class OrganizationTimetable extends Domain implements Comparable<OrganizationTimetable> {

    @Column(name = "day_of_week")
    @Enumerated(value = EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "description")
    private String description;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(OrganizationTimetable o) {
        return this.getDayOfWeek().getRussianNumber().compareTo(o.getDayOfWeek().getRussianNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof OrganizationTimetable)) {
            return false;
        }

        OrganizationTimetable that = (OrganizationTimetable) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(dayOfWeek, that.dayOfWeek)
            .append(startTime, that.startTime)
            .append(endTime, that.endTime)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(dayOfWeek)
            .append(startTime)
            .append(endTime)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("dayOfWeek", dayOfWeek)
            .append("startTime", startTime)
            .append("endTime", endTime)
            .append("description", description)
            .toString();
    }

}
