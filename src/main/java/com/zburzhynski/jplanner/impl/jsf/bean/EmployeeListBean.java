package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.PositionType.ASSISTANT;
import static com.zburzhynski.jplanner.api.domain.PositionType.DOCTOR;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.impl.domain.Employee;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Employee list bean.
 * <p/>
 * Date: 02.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class EmployeeListBean implements Serializable {

    private List<Employee> doctors;
    private List<Employee> assistants;
    private List<Employee> employees;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        doctors = employeeService.getByPosition(DOCTOR);
        assistants = employeeService.getByPosition(ASSISTANT);
        employees = employeeService.getAll();
    }

    /**
     * Gets doctors of dental clinic.
     *
     * @return doctors of dental clinic
     */
    public List<Employee> getDoctors() {
        return doctors;
    }

    /**
     * Gets assistants of dental clinic.
     *
     * @return assistants of dental clinic
     */
    public List<Employee> getAssistants() {
        return assistants;
    }

    /**
     * Gets employees of dental clinic.
     *
     * @return employees of dental clinic
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets employee service.
     *
     * @param employeeService employee service to set
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}
