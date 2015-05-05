package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Workplace.
 * <p/>
 * Date: 04.05.2015
 *
 * @author hexed2
 */

@Entity
@Table(schema = "jplanner", name = "workplace")
public class Workplace extends Domain {

    public static final String P_NAME = "name";
    public static final String P_DESCRIPTION = "description";
    public static final String P_CABINET = "cabinet";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cabinet_id")
    private Cabinet cabinet;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        if (!(o instanceof Workplace)) {
            return false;
        }

        Workplace that = (Workplace) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(cabinet, that.cabinet)
            .append(name, that.name)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(cabinet)
            .append(name)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("cabinet", cabinet)
            .append("name", name)
            .append("description", description)
            .toString();
    }

}