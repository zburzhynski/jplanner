package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_DOCTOR;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLE;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_TIMETABLES;
import static com.zburzhynski.jplanner.impl.domain.AvailableResource.P_WORKPLACE;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.Employee.P_PERSON;
import static com.zburzhynski.jplanner.impl.domain.Employee.P_POSITION;
import static com.zburzhynski.jplanner.impl.domain.Person.P_NAME;
import static com.zburzhynski.jplanner.impl.domain.Person.P_PATRONYMIC;
import static com.zburzhynski.jplanner.impl.domain.Person.P_SURNAME;
import static com.zburzhynski.jplanner.impl.domain.Position.P_POSITION_TYPE;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTA;
import static com.zburzhynski.jplanner.impl.domain.ResourceTimetable.P_QUOTAS;
import com.zburzhynski.jplanner.api.criteria.AvailableEmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.EmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.repository.IEmployeeRepository;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.CriteriaAlias;
import com.zburzhynski.jplanner.impl.domain.Employee;
import com.zburzhynski.jplanner.impl.util.CriteriaHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IEmployeeRepository} interface.
 * <p/>
 * Date: 02.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Repository("employeeRepository")
public class EmployeeRepository extends AbstractBaseRepository<String, Employee>
    implements IEmployeeRepository<String, Employee> {

    private static final String LOGIN = "user.login";

    private static final String ID = "id";

    private static final String DEVELOPER_EMPLOYEE_ID = "abf4b933-2ace-4b7b-9429-cce2a85cfc9b";

    @Override
    public Employee findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_PERSON, P_PERSON);
        criteria.createAlias(P_POSITION, P_POSITION);
        criteria.add(Restrictions.eq(P_ID, id));
        return (Employee) criteria.uniqueResult();
    }

    @Override
    public Employee findByLogin(String login) {
//        Criterion loginCriteria = Restrictions.eq(LOGIN, login);
//        List<Criterion> criteria = new ArrayList<>();
//        criteria.add(loginCriteria);
//        List<CriteriaAlias> aliases = new ArrayList<>();
//        aliases.add(new CriteriaAlias(USER, USER_ALIAS));
//        return findUniqueByCriteria(null, null, aliases, null, null, null, criteria);
        return new Employee();
    }

    @Override
    public List<Employee> findByPosition(PositionType positionType) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(getDomainClass());
        criteria.createAlias(P_PERSON, P_PERSON);
        criteria.createAlias(P_POSITION, P_POSITION);
        criteria.add(Restrictions.eq(P_POSITION + DOT + P_POSITION_TYPE, positionType));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_SURNAME));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_NAME));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_PATRONYMIC));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Employee> findAvailable(AvailableEmployeeSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        DetachedCriteria resourceCriteria = DetachedCriteria.forClass(AvailableResource.class);
        resourceCriteria.setProjection(Projections.distinct(Projections.property(P_DOCTOR + DOT + P_ID)));
        resourceCriteria.createAlias(P_TIMETABLES, P_TIMETABLE);
        resourceCriteria.createAlias(P_TIMETABLE + DOT + P_QUOTAS, P_QUOTA);
        resourceCriteria.createAlias(P_WORKPLACE, P_WORKPLACE);
        resourceCriteria.add(Restrictions.eq(P_WORKPLACE + DOT + P_ID, searchCriteria.getWorkplaceId()));
        resourceCriteria.add(Restrictions.in(P_QUOTA + DOT + P_ID, searchCriteria.getQuotaIds()));
        criteria.add(Subqueries.propertyIn(P_ID, resourceCriteria));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Employee> findByCriteria(EmployeeSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_PERSON, P_PERSON);
        criteria.createAlias(P_POSITION, P_POSITION);
        if (StringUtils.isNotEmpty(searchCriteria.getPositionId())) {
            criteria.add(Restrictions.eq(P_POSITION + DOT + P_ID, searchCriteria.getPositionId()));
        }
        CriteriaHelper.addPagination(criteria, searchCriteria.getStart(), searchCriteria.getEnd());
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_SURNAME));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_NAME));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_PATRONYMIC));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public int countByCriteria(EmployeeSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        if (StringUtils.isNotEmpty(searchCriteria.getPositionId())) {
            criteria.createAlias(P_POSITION, P_POSITION);
            criteria.add(Restrictions.eq(P_POSITION + DOT + P_ID, searchCriteria.getPositionId()));
        }
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    @Override
    public List<Employee> findAll() {
        Criterion developerCriterion = Restrictions.ne(ID, DEVELOPER_EMPLOYEE_ID);
        List<Criterion> criteria = new ArrayList<>();
        criteria.add(developerCriterion);
        List<CriteriaAlias> aliases = new ArrayList<>();
        aliases.add(new CriteriaAlias(P_PERSON, P_PERSON));
        Map<String, Boolean> orders = getDefaultSorting();
        return findByCriteria(null, null, aliases, null, orders, null, criteria);
    }

    @Override
    public boolean isUsed(Employee employee) {
//        Criteria examCriteria = CriteriaHelper.isUsed(getSession(), FluoroExam.class, DOCTOR, employee);
//        List<IFluoroExam> exams = examCriteria.list();
//        if (CollectionUtils.isNotEmpty(exams)) {
//            return true;
//        }
//        List<IDentalVisit> visits;
//        Criteria visitDoctorCriteria = CriteriaHelper.isUsed(getSession(), DentalVisit.class,
//            DOCTOR, employee);
//        visits = visitDoctorCriteria.list();
//        if (CollectionUtils.isNotEmpty(visits)) {
//            return true;
//        }
//        Criteria visitAssistantCriteria = CriteriaHelper.isUsed(getSession(), DentalVisit.class,
//            ASSISTANT, employee);
//        visits = visitAssistantCriteria.list();
//        if (CollectionUtils.isNotEmpty(visits)) {
//            return true;
//        }
        return false;
    }

    @Override
    protected Class<? extends Employee> getDomainClass() {
        return Employee.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        Map<String, Boolean> fullNameOrders = new LinkedHashMap<>();
        fullNameOrders.put(P_PERSON + DOT + P_SURNAME, true);
        fullNameOrders.put(P_PERSON + DOT + P_NAME, true);
        fullNameOrders.put(P_PERSON + DOT + P_PATRONYMIC, true);
        return fullNameOrders;
    }

    /**
     * Builds aliases map.
     *
     * @return aliases map
     */
    private Map<String, String> buildAliases() {
        Map<String, String> aliases = new LinkedHashMap<>();
        aliases.put(P_PERSON, P_PERSON);
        aliases.put(P_POSITION, P_POSITION);
//        aliases.put(USER, USER_ALIAS);
        return aliases;
    }

}
