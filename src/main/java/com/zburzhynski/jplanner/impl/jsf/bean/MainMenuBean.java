package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.ABOUT;
import static com.zburzhynski.jplanner.api.domain.View.CABINETS;
import static com.zburzhynski.jplanner.api.domain.View.EMPLOYEES;
import static com.zburzhynski.jplanner.api.domain.View.POSITIONS;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENTS;
import com.zburzhynski.jplanner.impl.util.JsfUtils;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * Main menu bean.
 * <p/>
 * Date: 01.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class MainMenuBean implements Serializable {

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Redirects to schedules.xhtml page.
     *
     * @return path to redirect
     */
    public String schedules() {
        return SCHEDULE_EVENTS.getPath();
    }

    /**
     * External redirect to jdent.
     */
    public void jdent() {
        String url = configBean.getJdentUrl();
        JsfUtils.externalRedirect(url);
    }

    /**
     * Redirects to about.xhtml page.
     *
     * @return path to redirect
     */
    public String about() {
        return ABOUT.getPath();
    }

    /**
     * Redirects to cabinets.xhtml page.
     *
     * @return path to redirect
     */
    public String cabinets() {
        return CABINETS.getPath();
    }

    /**
     * Redirects to positions.xhtml page.
     *
     * @return path to redirect
     */
    public String positions() {
        return POSITIONS.getPath();
    }

    /**
     * Redirects to employees.xhtml page.
     *
     * @return path to redirect
     */
    public String employees() {
        return EMPLOYEES.getPath();
    }

    /**
     * Sets config bean.
     *
     * @param configBean {@link ConfigBean} config bean to set
     */
    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

}
