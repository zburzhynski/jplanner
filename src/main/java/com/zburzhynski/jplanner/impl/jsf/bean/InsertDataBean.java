package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.IScheduleService;
import com.zburzhynski.jplanner.impl.domain.Employee;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Insert test data bean.
 * <p/>
 * Date: 5/4/2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class InsertDataBean implements Serializable {

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    /**
     * Inserts test data to database.
     */
    public void insertData() {
        Employee doctor = new Employee();
        doctor.getPerson().setSurname("Клопов");
        doctor.getPerson().setName("Виталий");
        doctor.getPerson().setPatronymic("Юрьевич");
        doctor.getPosition().setId("11111");
        employeeService.saveOrUpdate(doctor);
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}
