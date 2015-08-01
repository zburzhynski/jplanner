package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Setting.P_SORT_ORDER;
import com.zburzhynski.jplanner.api.domain.SettingCategory;
import com.zburzhynski.jplanner.api.repository.ISettingRepository;
import com.zburzhynski.jplanner.impl.domain.Setting;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ISettingRepository} interface.
 * <p/>
 * Date: 09.06.15
 *
 * @author Vladimir Zburzhynski
 */
@Repository("settingRepository")
public class SettingRepository extends AbstractBaseRepository<String, Setting>
    implements ISettingRepository<String, Setting> {

    @Override
    public List<Setting> findByCategory(SettingCategory category) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.add(Restrictions.eq(Setting.P_CATEGORY, category));
        criteria.addOrder(Order.asc(Setting.P_SORT_ORDER));
        return criteria.list();
    }

    @Override
    protected Class<? extends Setting> getDomainClass() {
        return Setting.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        Map<String, Boolean> orders = new LinkedHashMap<>();
        orders.put(P_SORT_ORDER, true);
        return orders;
    }

}
