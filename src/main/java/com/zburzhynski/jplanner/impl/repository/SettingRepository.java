package com.zburzhynski.jplanner.impl.repository;

import static com.zburzhynski.jplanner.impl.domain.Setting.P_SORT_ORDER;
import com.zburzhynski.jplanner.api.repository.ISettingRepository;
import com.zburzhynski.jplanner.impl.domain.Setting;
import com.zburzhynski.jplanner.impl.domain.SettingCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        return new ArrayList<>();
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