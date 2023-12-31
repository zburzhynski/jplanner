package com.zburzhynski.jplanner.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Employee.
 * <p/>
 * Date: 02.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "employee")
public class Employee extends Domain {

    public static final String WHITE_COLOR = "f7fcfc";
    public static final String P_PERSON = "person";
    public static final String P_POSITION = "position";

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person = new Person();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_position_id")
    private Position position;

    @Column(name = "email")
    private String email;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "color")
    private String color = WHITE_COLOR;

//    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "application_user_id")
//    private IUser user = new User();

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //    public IUser getUser() {
//        return user;
//    }
//
//    public void setUser(IUser user) {
//        this.user = user;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Employee)) {
            return false;
        }

        Employee that = (Employee) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(person, that.person)
            .append(email, that.email)
            .append(additionalInfo, that.additionalInfo)
            .append(color, that.color)
//            .append(user, that.user)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(person)
            .append(email)
            .append(additionalInfo)
            .append(color)
//            .append(user)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append(super.toString())
            .append("person", person)
            .append("email", email)
            .append("additionalInfo", additionalInfo)
            .append("color", color)
//            .append("user", user)
            .toString();
    }

}
