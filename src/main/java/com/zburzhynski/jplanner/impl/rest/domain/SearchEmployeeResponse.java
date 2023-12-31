package com.zburzhynski.jplanner.impl.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Search employee response.
 * <p/>
 * Date: 18.06.2015
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class SearchEmployeeResponse implements Serializable {

    private List<EmployeeDto> employees = new ArrayList<>();

    private int totalCount;

    /**
     * Adds employee.
     *
     * @param employee {@link EmployeeDto} employee to add
     */
    public void addEmployee(EmployeeDto employee) {
        employees.add(employee);
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
