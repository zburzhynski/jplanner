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

        Cabinet cabinet1 = new Cabinet();
        cabinet1.setNumber("100а");
        cabinet1.setName("Дентар");
        cabinet1.setDescription("Хороший кабинет");
        cabinet1.getWorkplaces().add(workplace1);
        cabinet1.getWorkplaces().add(workplace2);
        cabinetService.saveOrUpdate(cabinet1);


        Workplace workplace3 = new Workplace();
        workplace3.setName("Кресло 3");
        workplace3.setDescription("Описание третьего кресла");

        Workplace workplace4 = new Workplace();
        workplace4.setName("Кресло 4");
        workplace4.setDescription("Описание четвертого кресла");

        Cabinet cabinet2 = new Cabinet();
        cabinet2.setNumber("200б");
        cabinet2.setName("Дисамед");
        cabinet2.setDescription("Отличный кабинет");
        cabinet2.getWorkplaces().add(workplace3);
        cabinet2.getWorkplaces().add(workplace4);

        cabinetService.saveOrUpdate(cabinet2);

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
