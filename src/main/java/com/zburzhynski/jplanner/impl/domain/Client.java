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
 * Client of clinic.
 * <p/>
 * Date: 12.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "client")
public class Client extends Domain {

    public static final String P_JDENT_PATIENT_ID = "jdentPatientId";
    public static final String P_PERSON = "person";

    @Column(name = "jdent_patient_id")
    private String jdentPatientId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person = new Person();

    @Column(name = "reason")
    private String reason;

    @Column(name = "additional_info")
    private String additionalInfo;

    public String getJdentPatientId() {
        return jdentPatientId;
    }

    public void setJdentPatientId(String jdentPatientId) {
        this.jdentPatientId = jdentPatientId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Client)) {
            return false;
        }

        Client that = (Client) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(jdentPatientId, that.jdentPatientId)
            .append(person, that.person)
            .append(reason, that.reason)
            .append(additionalInfo, that.additionalInfo)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(jdentPatientId)
            .append(person)
            .append(reason)
            .append(additionalInfo)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("jdentPatientId", jdentPatientId)
            .append("person", person)
            .append("reason", reason)
            .append("additionalInfo", additionalInfo)
            .toString();
    }

}
