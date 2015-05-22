package com.zburzhynski.jplanner.impl.rest.domain;


import com.zburzhynski.jplanner.api.domain.Gender;

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
@XmlType(propOrder = {"id", "name", "surname", "patronymic", "birthday", "gender"})
public class Patient implements Serializable {

    private String id;

    private String name;

    private String surname;

    private String patronymic;

    private Date birthday;

    private Gender gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

}
