package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.domain.SettingCategory;
import com.zburzhynski.jplanner.api.repository.ISettingRepository;
import com.zburzhynski.jplanner.api.service.ISettingService;
import com.zburzhynski.jplanner.impl.domain.Setting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ISettingService} interface.
 * <p/>
 * Date: 09.06.15
 *
 * @author Vladimir Zburzhynski
 */
@Service("settingService")
@Transactional(readOnly = true)
public class SettingService implements ISettingService<String, Setting> {

    @Autowired
    private ISettingRepository settingRepository;

    @Override
    public Setting getById(String id) {
        return (Setting) settingRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Setting setting) {
        boolean result = false;
        if (setting != null) {
            if (StringUtils.isBlank(setting.getId())) {
                settingRepository.insert(setting);
                result = true;
            } else {
                settingRepository.update(setting);
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Setting setting) {
        boolean deleted = false;
        if (setting != null) {
            settingRepository.delete(setting);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<Setting> getByCategory(SettingCategory category) {
        return settingRepository.findByCategory(category);
    }

    @Override
    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

}
