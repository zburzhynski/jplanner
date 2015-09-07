package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.AMPERSAND;
import static com.zburzhynski.jplanner.api.domain.CommonConstant.EQUAL;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.ITimetableService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Timetable bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TimetableBean implements Serializable {

    private static final String TIMETABLES_COMPONENT = "timetablesForm:timetables";
    private static final String EMPLOYEE_ID_PARAM = "employeeId";
    private static final String TIMETABLE_ID_PARAM = "timetableId";

    private String employeeId;

    private Employee employee;

    private ScheduleModel eventModel = new DefaultScheduleModel();

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    @ManagedProperty(value = "#{timetableService}")
    private ITimetableService timetableService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        String employeeIdParam = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get(EMPLOYEE_ID_PARAM);
        if (StringUtils.isBlank(employeeIdParam)) {
            List<Employee> employees = employeeService.getAll();
            employee = (Employee) employeeService.getById(employees.get(0).getId());
        } else {
            employee = (Employee) employeeService.getById(employeeIdParam);
            employeeId = employeeIdParam;
        }
    }

    /**
     * Adds timetable.
     *
     * @return path for navigating
     */
    public String addTimetable() {
        return View.TIMETABLE_TEMPLATE.getPath() + AMPERSAND + EMPLOYEE_ID_PARAM + EQUAL + employeeId;
    }

    /**
     * Edits timetable.
     *
     * @param editedTimetable edited timetable
     * @return path for navigating
     */
    public String editTimetable(Timetable editedTimetable) {
        return View.TIMETABLE.getPath() + AMPERSAND + TIMETABLE_ID_PARAM + EQUAL + editedTimetable.getId();
    }

    /**
     * Removes timetable.
     *
     * @param removedTimetable timetable to remove
     */
    public void removeTimetable(Timetable removedTimetable) {
        employee.getTimetables().remove(removedTimetable);
        employeeService.saveOrUpdate(employee);
        timetableService.delete(removedTimetable);
    }

    /**
     * Employee select listener.
     */
    public void employeeSelectListener() {
        employee = (Employee) employeeService.getById(employeeId);
        JsfUtils.update(TIMETABLES_COMPONENT);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setTimetableService(ITimetableService timetableService) {
        this.timetableService = timetableService;
    }

}
