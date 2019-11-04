package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.PositionType.ASSISTANT;
import static com.zburzhynski.jplanner.api.domain.PositionType.DOCTOR;
import static com.zburzhynski.jplanner.impl.jsf.bean.ScheduleBean.END_DATE_PARAM;
import static com.zburzhynski.jplanner.impl.jsf.bean.ScheduleBean.START_DATE_PARAM;
import static com.zburzhynski.jplanner.impl.jsf.bean.ScheduleBean.WORKPLACE_PARAM;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.util.JsfUtils;

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

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        Workplace workplace = (Workplace) JsfUtils.getFlashAttribute(WORKPLACE_PARAM);
        Date startDate = (Date) JsfUtils.getFlashAttribute(START_DATE_PARAM);
        Date endDate = (Date) JsfUtils.getFlashAttribute(END_DATE_PARAM);
        init(workplace, startDate, endDate);
    }

    /**
     * Inits bean state.
     *
     * @param workplace workplace
     * @param startDate start date
     * @param endDate   end date
     */
    public void init(Workplace workplace, Date startDate, Date endDate) {
//TODO: use checkQuotas setting
//        if (workplace == null || startDate == null || endDate == null) {
//            doctors = employeeService.getByPosition(DOCTOR);
//            assistants = employeeService.getByPosition(ASSISTANT);
//        } else {
//            AvailableEmployeeSearchCriteria availableDoctorCriteria = new AvailableEmployeeSearchCriteria();
//            availableDoctorCriteria.setWorkplaceId(workplace.getId());
//            availableDoctorCriteria.setStartDate(startDate);
//            availableDoctorCriteria.setEndDate(endDate);
//            doctors = employeeService.getAvailable(availableDoctorCriteria);
//
//            AvailableEmployeeSearchCriteria availableAssistantCriteria = new AvailableEmployeeSearchCriteria();
//            assistants = employeeService.getAvailable(availableAssistantCriteria);
//        }
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
