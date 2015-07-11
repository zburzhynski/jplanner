package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.impl.domain.SettingCategory.COMMON;
import static com.zburzhynski.jplanner.impl.domain.SettingCategory.JDENT;
import static com.zburzhynski.jplanner.impl.domain.SettingCategory.VIEW;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.api.service.ISettingService;
import com.zburzhynski.jplanner.impl.domain.Setting;
import org.apache.commons.lang.BooleanUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * Application configuration bean.
 * <p/>
 * Date: 06.06.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ApplicationScoped
public class ConfigBean implements Serializable {

    private static final String P_PATIENTS_PER_PAGE = "patients_per_page";

    private static final String P_EMPLOYEES_PER_PAGE = "employees_per_page";

    private static final String P_CABINETS_PER_PAGE = "cabinets_per_page";

    private static final String P_POSITIONS_PER_PAGE = "job_positions_per_page";

    private static final String P_JDENT_INTEGRATION_ENABLED = "jdent_integration_enabled";

    private static final String P_JDENT_URL = "jdent_url";

    private Setting setting;

    private Map<String, Setting> settings = new HashMap();

    private List<Setting> commonSettings;

    private List<Setting> viewSettings;

    private List<Setting> jdentSettings;

    private int tabIndex;

    @ManagedProperty(value = "#{settingService}")
    private ISettingService settingService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        List<Setting> all = settingService.getAll();
        for (Setting item : all) {
            settings.put(item.getName(), item);
        }
        commonSettings = settingService.getByCategory(COMMON);
        viewSettings = settingService.getByCategory(VIEW);
        jdentSettings = settingService.getByCategory(JDENT);
    }

    /**
     * Saves setting.
     *
     * @return path for navigating
     */
    public String saveSetting() {
        settingService.saveOrUpdate(setting);
        init();
        return View.SETTINGS.getPath();
    }

    /**
     * Gets common settings.
     *
     * @return common settings
     */
    public List<Setting> getCommonSettings() {
        return commonSettings;
    }

    /**
     * Gets view settings.
     *
     * @return view settings
     */
    public List<Setting> getViewSettings() {
        return viewSettings;
    }

    /**
     * Gets jdent integration settings.
     *
     * @return jdent integration settings
     */
    public List<Setting> getJdentSettings() {
        return jdentSettings;
    }

    /**
     * Gets patients per page count.
     *
     * @return patients per page count
     */
    public int getPatientsPerPageCount() {
        return Integer.parseInt(settings.get(P_PATIENTS_PER_PAGE).getValue());
    }

    /**
     * Gets employees per page count.
     *
     * @return employees per page count
     */
    public int getEmployeesPerPageCount() {
        return Integer.parseInt(settings.get(P_EMPLOYEES_PER_PAGE).getValue());
    }

    /**
     * Gets cabinets per page count.
     *
     * @return cabinets per page count
     */
    public int getCabinetsPerPageCount() {
        return Integer.parseInt(settings.get(P_CABINETS_PER_PAGE).getValue());
    }

    /**
     * Gets positions per page count.
     *
     * @return positions per page count
     */
    public int getPositionsPerPageCount() {
        return Integer.parseInt(settings.get(P_POSITIONS_PER_PAGE).getValue());
    }

    /**
     * Is jdent integration enabled.
     *
     * @return true if jdent integration enabled, else false
     */
    public boolean isJdentIntegrationEnabled() {
        return BooleanUtils.toBoolean(settings.get(P_JDENT_INTEGRATION_ENABLED).getValue());
    }

    /**
     * Jdent integration url.
     *
     * @return jdent integration url
     */
    public String getJdentUrl() {
        return settings.get(P_JDENT_URL).getValue();
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }

}
