package com.zburzhynski.jplanner.impl.domain;

import com.zburzhynski.jplanner.api.domain.TimetableStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Available resource timetable.
 * <p/>
 * Date: 18.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "resource_timetable")
public class ResourceTimetable extends Domain implements Comparable<ResourceTimetable> {

    public static final String P_AVAILABLE_RESOURCE = "availableResource";
    public static final String P_TIMETABLE_STATUS = "status";
    public static final String P_QUOTAS = "quotas";
    public static final String P_QUOTA = "quota";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "available_resource_id")
    private AvailableResource availableResource;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TimetableStatus status = TimetableStatus.DRAFT;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quota> quotas;

    public AvailableResource getAvailableResource() {
        return availableResource;
    }

    public void setAvailableResource(AvailableResource availableResource) {
        this.availableResource = availableResource;
    }

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

    public TimetableStatus getStatus() {
        return status;
    }

    public void setStatus(TimetableStatus status) {
        this.status = status;
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

    /**
     * Adds quota to timetable.
     *
     * @param quota quota to add
     */
    public void addQuota(Quota quota) {
        quota.setTimetable(this);
        getQuotas().add(quota);
    }

    /**
     * Removes quota from timetable.
     *
     * @param quota quota to remove
     */
    public void removeQuota(Quota quota) {
        getQuotas().remove(quota);
    }

    @Override
    public int compareTo(ResourceTimetable o) {
        return this.getStartDate().compareTo(o.startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ResourceTimetable)) {
            return false;
        }

        ResourceTimetable that = (ResourceTimetable) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(startDate, that.startDate)
            .append(endDate, that.endDate)
            .append(status, that.status)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(startDate)
            .append(endDate)
            .append(status)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("startDate", startDate)
            .append("endDate", endDate)
            .append("status", status)
            .append("description", description)
            .toString();
    }

}
