package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Position;

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

    @ManagedProperty(value = "#{positionService}")
    private IPositionService positionService;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    /**
     * Inserts test data to database.
     */
    public void insertData() {
        Position doctorPosition = new Position();
        doctorPosition.setName("Врач-терапевт");
        doctorPosition.setPositionType(PositionType.DOCTOR);
        positionService.saveOrUpdate(doctorPosition);

        Employee doctor = new Employee();
        doctor.getPerson().setSurname("Клопов");
        doctor.getPerson().setName("Виталий");
        doctor.getPerson().setPatronymic("Юрьевич");
        doctor.setPosition(doctorPosition);
        employeeService.saveOrUpdate(doctor);
    }

    public void setPositionService(IPositionService positionService) {
        this.positionService = positionService;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}
