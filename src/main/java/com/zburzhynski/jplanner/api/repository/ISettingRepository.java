package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.domain.SettingCategory;

import java.util.List;

/**
 * Setting repository interface.
 * <p/>
 * Date: 09.06.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ISettingRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds settings by category.
     *
     * @param category {@link SettingCategory} setting category
     * @return settings
     */
    List<T> findByCategory(SettingCategory category);

}
