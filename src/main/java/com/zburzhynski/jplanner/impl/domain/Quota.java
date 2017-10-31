package com.zburzhynski.jplanner.impl.domain;

import com.zburzhynski.jplanner.api.domain.QuotaType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Quota.
 * <p/>
 * Date: 18.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "quota")
public class Quota extends Domain implements Comparable<Quota> {

    public static final String P_TIMETABLE = "timetable";
    public static final String P_START_DATE = "startDate";
    public static final String P_END_DATE = "endDate";
    public static final String P_QUOTA_TYPE = "quotaType";
    public static final String P_DESCRIPTION = "description";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_timetable_id")
    private ResourceTimetable timetable;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "quota_type")
    @Enumerated(value = EnumType.STRING)
    private QuotaType quotaType;

    @Column(name = "description")
    private String description;

    /**
     * Default constructor.
     */
    public Quota() {}

    /**
     * Constructor.
     *
     * @param startDate     start date
     * @param endDate       end date
     * @param quotaType     quota type
     */
    public Quota(Date startDate, Date endDate, QuotaType quotaType) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.quotaType = quotaType;
    }

    public ResourceTimetable getTimetable() {
        return timetable;
    }

    public void setTimetable(ResourceTimetable timetable) {
        this.timetable = timetable;
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

    public QuotaType getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(QuotaType quotaType) {
        this.quotaType = quotaType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Quota o) {
        return this.getStartDate().compareTo(o.startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Quota)) {
            return false;
        }

        Quota that = (Quota) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(startDate, that.startDate)
            .append(endDate, that.endDate)
            .append(quotaType, that.quotaType)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(startDate)
            .append(endDate)
            .append(quotaType)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("startDate", startDate)
            .append("endDate", endDate)
            .append("quotaType", quotaType)
            .append("description", description)
            .toString();
    }

}
