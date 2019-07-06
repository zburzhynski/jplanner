package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.EMPLOYEE;
import static com.zburzhynski.jplanner.api.domain.View.EMPLOYEES;
import com.zburzhynski.jplanner.api.criteria.EmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.api.exception.LinkedAvailableResourceExistException;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Position;
import com.zburzhynski.jplanner.impl.rest.client.IEmployeeRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.EmployeeDto;
import com.zburzhynski.jplanner.impl.rest.domain.JobPosition;
import com.zburzhynski.jplanner.impl.rest.domain.PositionDto;
import com.zburzhynski.jplanner.impl.rest.domain.SearchEmployeeResponse;
import com.zburzhynski.jplanner.impl.util.MessageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Employee bean.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class EmployeeBean implements Serializable {

    private static final String LINKED_AVAILABLE_RESOURCE_EXIST = "employee.linkedAvailableResourceExist";

    private Employee employee;

    private LazyDataModel<Employee> employeeModel;

    @ManagedProperty(value = "#{employeeRestClient}")
    private IEmployeeRestClient employeeRestClient;

    @ManagedProperty(value = "#{employeeService}")
    private IEmployeeService employeeService;

    @ManagedProperty(value = "#{positionService}")
    private IPositionService positionService;

    @ManagedProperty(value = "#{messageHelper}")
    private MessageHelper messageHelper;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        employeeModel = new LazyDataModel<Employee>() {
            @Override
            public List<Employee> load(int first, int pageSize, String sortField,
                                       SortOrder sortOrder, Map<String, Object> filters) {
                EmployeeSearchCriteria searchCriteria = new EmployeeSearchCriteria();
                searchCriteria.setStart(Long.valueOf(first));
                searchCriteria.setEnd(Long.valueOf(first + pageSize));
                List<Employee> employees = employeeService.getByCriteria(searchCriteria);
                int count = employeeService.countByCriteria(searchCriteria);
                setRowCount(count);
                return employees;
            }
        };
    }

    /**
     * Adds employee.
     *
     * @return path for navigating
     */
    public String addEmployee() {
        employee = new Employee();
        return EMPLOYEE.getPath();
    }

    /**
     * Edits employee.
     *
     * @return path for navigating
     */
    public String editEmployee() {
        return EMPLOYEE.getPath();
    }

    /**
     * Saves employee.
     *
     * @return path for navigating
     */
    public String saveEmployee() {
        employeeService.saveOrUpdate(employee);
        return EMPLOYEES.getPath();
    }

    /**
     * Removes employee.
     *
     * @return path for navigating
     */
    public String removeEmployee() {
        try {
            employeeService.delete(employee);
        } catch (LinkedAvailableResourceExistException e) {
            messageHelper.addErrorMessage(LINKED_AVAILABLE_RESOURCE_EXIST);
            return null;
        }
        return EMPLOYEES.getPath();
    }

    /**
     * Refresh employee reference.
     */
    public void refresh() {
        SearchEmployeeResponse response = employeeRestClient.getAll(configBean.getJdentUrl());
        if (response != null && CollectionUtils.isNotEmpty(response.getEmployees())) {
            for (EmployeeDto employeeDto : response.getEmployees()) {
                if (BooleanUtils.isTrue(employeeDto.getActual())) {
                    PositionDto positionDto = employeeDto.getPosition();
                    Position positionSrc = (Position) positionService.getById(positionDto.getId());
                    if (positionSrc == null) {
                        positionSrc = new Position();
                        positionSrc.setId(positionDto.getId());
                        updatePosition(positionSrc, positionDto);
                        positionService.replicate(positionSrc);
                    } else {
                        updatePosition(positionSrc, positionDto);
                        positionService.saveOrUpdate(positionSrc);
                    }
                    Employee employeeSrc = (Employee) employeeService.getById(employeeDto.getId());
                    if (employeeSrc == null) {
                        employeeSrc = new Employee();
                        employeeSrc.setId(employeeDto.getId());
                        employeeSrc.getPerson().setId(UUID.randomUUID().toString());
                        updateEmployee(employeeSrc, employeeDto);
                        employeeSrc.setPosition(positionSrc);
                        employeeService.replicate(employeeSrc);
                    } else {
                        updateEmployee(employeeSrc, employeeDto);
                        employeeSrc.setPosition(positionSrc);
                        employeeService.saveOrUpdate(employeeSrc);
                    }
                }
            }
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LazyDataModel<Employee> getEmployeeModel() {
        return employeeModel;
    }

    public Integer getRowCount() {
        return configBean.getEmployeesPerPageCount();
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

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    private void updatePosition(Position positionSrc, PositionDto positionDto) {
        positionSrc.setName(positionDto.getName());
        positionSrc.setPositionType(JobPosition.getById(positionDto.getId()));
    }

    private void updateEmployee(Employee employeeSrc, EmployeeDto employeeDto) {
        employeeSrc.getPerson().setName(employeeDto.getName());
        employeeSrc.getPerson().setSurname(employeeDto.getSurname());
        employeeSrc.getPerson().setPatronymic(employeeDto.getPatronymic());
        employeeSrc.getPerson().setBirthday(employeeDto.getBirthday());
        employeeSrc.getPerson().setGender(employeeDto.getGender().equals(Gender.M.name())
            ? Gender.M : Gender.F);
        employeeSrc.setEmail(employeeDto.getEmail());
        employeeSrc.setAdditionalInfo(employeeDto.getAdditionalInformation());
    }

}
