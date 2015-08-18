package com.zburzhynski.jplanner.impl.domain;

import com.zburzhynski.jplanner.api.domain.QuotaType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Entity;
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
public class Quota extends Domain {

    private Date startDate;

    private Date endDate;

    private QuotaType quotaType;

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
