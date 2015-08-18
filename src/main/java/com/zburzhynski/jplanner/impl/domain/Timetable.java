package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import javax.persistence.Entity;
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
public class Timetable extends Domain {

    private String description;

    private List<Quota> quotas;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Quota> getQuotas() {
        return quotas;
    }

    public void setQuotas(List<Quota> quotas) {
        this.quotas = quotas;
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
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("description", description)
            .toString();
    }

}
