package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Available resource.
 * <p/>
 * Date: 11.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(schema = "jplanner", name = "available_resource")
public class AvailableResource extends Domain {

    public static final String P_TIMETABLES = "timetables";
    public static final String P_TIMETABLE = "timetable";
    public static final String P_DOCTOR = "doctor";
    public static final String P_ASSISTANT = "assistant";
    public static final String P_WORKPLACE = "workplace";

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Employee doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_id")
    private Employee assistant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "availableResource", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Timetable> timetables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public Employee getAssistant() {
        return assistant;
    }

    public void setAssistant(Employee assistant) {
        this.assistant = assistant;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets timetables.
     *
     * @return timetables
     */
    public Set<Timetable> getTimetables() {
        if (timetables == null) {
            timetables = new TreeSet<>();
        }
        return timetables;
    }

    /**
     * Sets timetables.
     *
     * @param timetables timetables to set
     */
    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    /**
     * Adds timetable to available resource.
     *
     * @param timetable timetable for add
     */
    public void addTimetable(Timetable timetable) {
        timetable.setAvailableResource(this);
        getTimetables().add(timetable);
    }

    /**
     * Remove timetable from available resource.
     *
     * @param timetable timetable for remove
     */
    public void removeTimetable(Timetable timetable) {
        getTimetables().remove(timetable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AvailableResource)) {
            return false;
        }

        AvailableResource that = (AvailableResource) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("description", description)
            .toString();
    }

}
