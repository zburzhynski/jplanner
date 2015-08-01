package com.zburzhynski.jplanner.api.service;

import com.zburzhynski.jplanner.api.domain.IDomain;
import com.zburzhynski.jplanner.api.domain.SettingCategory;

import java.util.List;

/**
 * Setting service interface.
 * <p/>
 * Date: 09.06.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ISettingService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets settings by category.
     *
     * @param category {@link com.zburzhynski.jplanner.api.domain.SettingCategory} setting category
     * @return settings
     */
    List<T> getByCategory(SettingCategory category);

}
