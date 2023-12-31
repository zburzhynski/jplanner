package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Cabinet.
 * <p/>
 * Date: 02.05.2015
 *
 * @author Mikalai Karabeika
 */
@Entity
@Table(schema = "jplanner", name = "cabinet")
public class Cabinet extends Domain {

    public static final String P_NUMBER = "number";
    public static final String P_NAME = "name";
    public static final String P_DESCRIPTION = "description";
    public static final String P_WORKPLACE = "workplace";
    public static final String P_WORKPLACES = "workplaces";

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workplace> workplaces;

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

    /**
     * Gets workplaces.
     *
     * @return workplaces
     */
    public List<Workplace> getWorkplaces() {
        if (workplaces == null) {
            workplaces = new ArrayList<>();
        }
        return workplaces;
    }

    /**
     * Sets workplaces.
     *
     * @param workplaces workplaces to set
     */
    public void setWorkplaces(List<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    /**
     * Adds workplace to cabinet.
     *
     * @param workplace workplace to add
     */
    public void addWorkplace(Workplace workplace) {
        workplace.setCabinet(this);
        getWorkplaces().add(workplace);
    }

    /**
     * Removes workplace from cabinet.
     *
     * @param workplace workplace to remove
     */
    public void removeWorkplace(Workplace workplace) {
        getWorkplaces().remove(workplace);
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
