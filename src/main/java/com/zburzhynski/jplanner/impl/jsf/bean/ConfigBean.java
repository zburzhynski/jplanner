package com.zburzhynski.jplanner.impl.jsf.bean;

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

    private static final String P_JDENT_INTEGRATION_ENABLED = "jdent_integration_enabled";

    private static final String P_JDENT_URL = "jdent_url";

    private Map<String, Setting> settings = new HashMap();

    @ManagedProperty(value = "#{settingService}")
    private ISettingService settingService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        List<Setting> all = settingService.getAll();
        for (Setting setting : all) {
            settings.put(setting.getName(), setting);
        }
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

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }

}
