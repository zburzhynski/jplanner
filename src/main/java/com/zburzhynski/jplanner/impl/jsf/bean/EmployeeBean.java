package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Position;
import com.zburzhynski.jplanner.impl.rest.client.IEmployeeRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.EmployeeDto;
import com.zburzhynski.jplanner.impl.rest.domain.JobPosition;
import com.zburzhynski.jplanner.impl.rest.domain.PositionDto;
import com.zburzhynski.jplanner.impl.rest.domain.SearchEmployeeResponse;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Employee bean.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class EmployeeBean implements Serializable {

    @ManagedProperty(value = "#{employeeRestClient}")
    private IEmployeeRestClient employeeRestClient;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    @ManagedProperty(value = "#{positionService}")
    private IPositionService positionService;

    /**
     * Refresh employee reference.
     */
    public void refresh() {
        SearchEmployeeResponse response = employeeRestClient.getAll();
        if (response != null && CollectionUtils.isNotEmpty(response.getEmployees())) {
            for (EmployeeDto employeeDto : response.getEmployees()) {
                PositionDto positionDto = employeeDto.getPosition();
                Position position = (Position) positionService.getById(positionDto.getId());
                if (position == null) {
                    position = new Position();
                    position.setId(positionDto.getId());
                    updatePosition(position, positionDto);
                    positionService.replicate(position);
                } else {
                    updatePosition(position, positionDto);
                    positionService.saveOrUpdate(position);
                }
                Employee employee = (Employee) employeeService.getById(employeeDto.getId());
                if (employee == null) {
                    employee = new Employee();
                    employee.setId(employeeDto.getId());
                    employee.getPerson().setId(UUID.randomUUID().toString());
                    updateEmployee(employee, employeeDto);
                    employee.setPosition(position);
                    employeeService.replicate(employee);
                } else {
                    updateEmployee(employee, employeeDto);
                    employee.setPosition(position);
                    employeeService.saveOrUpdate(employee);
                }
            }
        }
    }

    public void setEmployeeRestClient(IEmployeeRestClient employeeRestClient) {
        this.employeeRestClient = employeeRestClient;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setPositionService(IPositionService positionService) {
        this.positionService = positionService;
    }

    private void updatePosition(Position position, PositionDto positionDto) {
        position.setName(positionDto.getName());
        position.setPositionType(JobPosition.getById(positionDto.getId()));
    }

    private void updateEmployee(Employee employee, EmployeeDto employeeDto) {
        employee.getPerson().setName(employeeDto.getName());
        employee.getPerson().setSurname(employeeDto.getSurname());
        employee.getPerson().setPatronymic(employeeDto.getPatronymic());
        employee.getPerson().setBirthday(employeeDto.getBirthday());
        employee.getPerson().setGender(employeeDto.getGender().equals(Gender.M.name())
            ? Gender.M : Gender.F);
        employee.setEmail(employeeDto.getEmail());
        employee.setAdditionalInfo(employeeDto.getAdditionalInformation());
    }

}
