package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jplanner.impl.domain.Domain.P_ID;
import static com.zburzhynski.jplanner.impl.domain.Employee.P_PERSON;
import static com.zburzhynski.jplanner.impl.domain.Employee.P_POSITION;
import static com.zburzhynski.jplanner.impl.domain.Person.P_NAME;
import static com.zburzhynski.jplanner.impl.domain.Person.P_PATRONYMIC;
import static com.zburzhynski.jplanner.impl.domain.Person.P_SURNAME;
import static com.zburzhynski.jplanner.impl.domain.Position.P_POSITION_TYPE;
import com.zburzhynski.jplanner.api.domain.PositionType;
import com.zburzhynski.jplanner.api.repository.IEmployeeRepository;
import com.zburzhynski.jplanner.impl.domain.CriteriaAlias;
import com.zburzhynski.jplanner.impl.domain.Employee;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
    public Employee findByLogin(String login) {
//        Criterion loginCriteria = Restrictions.eq(LOGIN, login);
//        List<Criterion> criteria = new ArrayList<>();
//        criteria.add(loginCriteria);
//        List<CriteriaAlias> aliases = new ArrayList<>();
//        aliases.add(new CriteriaAlias(USER, USER_ALIAS));
//        return findUniqueByCriteria(null, null, aliases, null, null, null, criteria);
        return new Employee();
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<Employee> findRange(Long start, Long end, SortCriteria[] sortCriteria, Map<String, String> filters) {
//        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Employee.class);
//        criteria.add(Restrictions.ne(ID, DEVELOPER_EMPLOYEE_ID));
//        Map<String, JavaType> types = new HashMap<>();
//        types.put(P_BIRTHDAY, JavaType.DATE);
//        return findRange(start, end, buildAliases(), types, sortCriteria, filters, criteria);
//    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Integer countByRange(Map<String, String> filters) {
//        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Employee.class);
//        criteria.add(Restrictions.ne(ID, DEVELOPER_EMPLOYEE_ID));
//        Map<String, JavaType> types = new HashMap<>();
//        types.put(BIRTHDAY, JavaType.DATE);
//        return countByRange(buildAliases(), types, filters, criteria);
//    }

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
