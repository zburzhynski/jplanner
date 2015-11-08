package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.PositionType.ASSISTANT;
import static com.zburzhynski.jplanner.api.domain.PositionType.DOCTOR;
import com.zburzhynski.jplanner.api.criteria.AvailableEmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Workplace;

import java.io.Serializable;
import java.util.Date;
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

    private Workplace workplace;
    private Date startDate;
    private Date endDate;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        if (workplace == null && startDate == null && endDate == null) {
            doctors = employeeService.getByPosition(DOCTOR);
            assistants = employeeService.getByPosition(ASSISTANT);
        } else {
            AvailableEmployeeSearchCriteria availableDoctorCriteria = new AvailableEmployeeSearchCriteria();
            doctors = employeeService.getAvailable(availableDoctorCriteria);

            AvailableEmployeeSearchCriteria availableAssistantCriteria = new AvailableEmployeeSearchCriteria();
            assistants = employeeService.getAvailable(availableAssistantCriteria);
        }
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


    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
