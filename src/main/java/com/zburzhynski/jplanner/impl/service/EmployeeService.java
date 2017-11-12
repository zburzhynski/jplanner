package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.AvailableEmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.EmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.IntersectedQuotaSearchCriteria;
import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.repository.IEmployeeRepository;
import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.api.service.IEmployeeService;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Implementation of {@link IEmployeeService} interface.
 * <p/>
 * Date: 01.03.13
 *
 * @author Vladimir Zburzhynski
 */
@Service("employeeService")
@Transactional(readOnly = true)
public class EmployeeService implements IEmployeeService<String, Employee> {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private IQuotaRepository quotaRepository;

    @Override
    public Employee getById(String id) {
        return (Employee) employeeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Employee employee) {
        boolean result = false;
        if (employee != null) {
            if (StringUtils.isEmpty(employee.getId())) {
                employeeRepository.insert(employee);
                result = true;
            } else {
                employeeRepository.update(employee);
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void replicate(Employee employee) {
        employeeRepository.replicate(employee);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public Employee getByLogin(String login) {
        return (Employee) employeeRepository.findByLogin(login);
    }

    @Override
    public List<Employee> getByPosition(PositionType positionType) {
        return employeeRepository.findByPosition(positionType);
    }

    @Override
    public List<Employee> getAvailable(AvailableEmployeeSearchCriteria searchCriteria) {
        IntersectedQuotaSearchCriteria quotaSearchCriteria = new IntersectedQuotaSearchCriteria();
        quotaSearchCriteria.setStartDate(searchCriteria.getStartDate());
        quotaSearchCriteria.setEndDate(searchCriteria.getEndDate());
        quotaSearchCriteria.setWorkplaceId(searchCriteria.getWorkplaceId());
        List<Quota> intersectingQuotas = quotaRepository.findIntersecting(quotaSearchCriteria);
        if (CollectionUtils.isEmpty(intersectingQuotas)) {
            return new ArrayList<>();
        }
        for (Quota quota : intersectingQuotas) {
            if (!QuotaType.WORK_TIME.equals(quota.getQuotaType())) {
                return new ArrayList<>();
            }
        }
        SortedSet<Quota> sortedQuotas = new TreeSet<>(intersectingQuotas);
        if (sortedQuotas.first().getStartDate().after(searchCriteria.getStartDate())
            || sortedQuotas.last().getEndDate().before(searchCriteria.getEndDate())) {
            return new ArrayList<>();
        }
        List<String> quotaIds = new ArrayList<>();
        for (Quota quota : intersectingQuotas) {
            quotaIds.add(quota.getId());
        }
        searchCriteria.setQuotaIds(quotaIds);
        return employeeRepository.findAvailable(searchCriteria);
    }

    @Override
    public List<Employee> getByCriteria(EmployeeSearchCriteria searchCriteria) {
        return employeeRepository.findByCriteria(searchCriteria);
    }

    @Override
    public int countByCriteria(EmployeeSearchCriteria searchCriteria) {
        return employeeRepository.countByCriteria(searchCriteria);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public boolean isUsed(Employee employee) {
        return employeeRepository.isUsed(employee);
    }

}
