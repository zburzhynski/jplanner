package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Timetable.
 * <p/>
 * Date: 18.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "timetable")
public class Timetable extends Domain implements Comparable<Timetable> {

    public static final String P_QUOTAS = "quotas";
    public static final String P_QUOTA = "quota";

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "timetable_quota",
        joinColumns = {@JoinColumn(name = "timetable_id")},
        inverseJoinColumns = {@JoinColumn(name = "quota_id")})
    private Set<Quota> quotas;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets timetable quotas.
     *
     * @return timetable quotas
     */
    public Set<Quota> getQuotas() {
        if (quotas == null) {
            quotas = new TreeSet<>();
        }
        return quotas;
    }

    /**
     * Sets timetable quotas.
     *
     * @param quotas timetable quotas to set
     */
    public void setQuotas(Set<Quota> quotas) {
        this.quotas = quotas;
    }

    @Override
    public int compareTo(Timetable o) {
        return this.getStartDate().compareTo(o.startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Timetable)) {
            return false;
        }

        Timetable that = (Timetable) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(startDate, that.startDate)
            .append(endDate, that.endDate)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(startDate)
            .append(endDate)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("startDate", startDate)
            .append("endDate", endDate)
            .append("description", description)
            .toString();
    }

}
