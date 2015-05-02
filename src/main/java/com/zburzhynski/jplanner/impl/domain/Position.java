package com.zburzhynski.jplanner.impl.domain;

import com.zburzhynski.jplanner.api.domain.PositionType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Job title.
 * <p/>
 * Date: 02.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "job_position")
public class Position extends Domain {

    public static final String P_NAME = "name";
    public static final String P_POSITION_TYPE = "positionType";

    @Column(name = "name")
    private String name;

    @Column(name = "position_type")
    @Enumerated(value = EnumType.STRING)
    private PositionType positionType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Position)) {
            return false;
        }

        Position that = (Position) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(positionType, that.positionType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(positionType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append(super.toString())
            .append("name", name)
            .append("positionType", positionType)
            .toString();
    }

}
