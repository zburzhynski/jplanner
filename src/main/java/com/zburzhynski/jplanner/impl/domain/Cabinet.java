package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Cabinet.
 * <p/>
 * Date: 02.05.2015
 *
 * @author hexed2
 */
@Entity
@Table(name = "cabinet")
public class Cabinet  extends Domain {

    public static final String P_NUMBER = "number";

    public static final String P_NAME = "name";

    public static final String P_DESCRIPTION = "description";

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

        if (!(o instanceof Cabinet)) {
            return false;
        }

        Cabinet that = (Cabinet) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(number, that.number)
                .append(name, that.name)
                .append(description, that.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(number)
                .append(name)
                .append(description)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("number", number)
                .append("name", name)
                .append("description", description)
                .toString();
    }
}
