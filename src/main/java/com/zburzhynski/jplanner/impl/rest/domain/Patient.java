package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlType;

/**
 * Person.
 * <p/>
 * Date: 21.05.15
 *
 * @author Vladimir Zburzhynski
 */
@XmlType(propOrder = {"id", "number", "name", "surname", "patronymic", "birthday", "gender", "address"})
public class Patient implements Serializable {

    private String id;

    private Integer number;

    private String name;

    private String surname;

    private String patronymic;

    private Date birthday;

    private String gender;

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
