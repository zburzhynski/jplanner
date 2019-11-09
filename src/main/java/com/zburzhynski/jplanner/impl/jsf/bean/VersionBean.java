package com.zburzhynski.jplanner.impl.jsf.bean;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Program version bean, represents information about program version.
 * <p/>
 * Date: 21.04.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ApplicationScoped
public class VersionBean implements Serializable {

    private String version = "2.1.0-snapshot";

    private String versionDate = "09.11.2019";

    /**
     * Gets program version.
     *
     * @return program version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets version date.
     *
     * @return version date
     */
    public String getVersionDate() {
        return versionDate;
    }

}
