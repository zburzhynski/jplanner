package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Position;
import com.zburzhynski.jplanner.impl.domain.Workplace;

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

    @ManagedProperty(value = "#{cabinetService}")
    private ICabinetService cabinetService;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    /**
     * Inserts test data to database.
     */
    public void insertData() {
        Workplace workplace1 = new Workplace();
        workplace1.setName("Кресло 1");
        workplace1.setDescription("Описание первого кресла");

        Workplace workplace2 = new Workplace();
        workplace2.setName("Кресло 2");
        workplace2.setDescription("Описание второго кресла");

        Cabinet cabinet = new Cabinet();
        cabinet.setNumber("100а");
        cabinet.setName("Дентар");
        cabinet.setDescription("Хороший кабинет");
        cabinet.getWorkplaces().add(workplace1);
        cabinet.getWorkplaces().add(workplace2);
        cabinetService.saveOrUpdate(cabinet);

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

    public void setCabinetService(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}
