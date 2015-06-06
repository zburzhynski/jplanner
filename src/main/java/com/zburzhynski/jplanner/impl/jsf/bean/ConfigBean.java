package com.zburzhynski.jplanner.impl.jsf.bean;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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

    /**
     * Is integration enabled.
     *
     * @return true if integration enabled, else false
     */
    public boolean isIntegrationEnabled() {
        return true;
    }

}
