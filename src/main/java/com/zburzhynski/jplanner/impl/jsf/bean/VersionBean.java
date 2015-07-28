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

    private String version = "1.0.0";

    private String versionDate = "29.07.2015";

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
