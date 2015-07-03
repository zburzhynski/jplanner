package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.View;
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
        return View.SCHEDULE_EVENTS.getPath();
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
        return View.ABOUT.getPath();
    }

    /**
     * Redirects to cabinets.xhtml page.
     *
     * @return path to redirect
     */
    public String cabinets() {
        return View.CABINETS.getPath();
    }

    /**
     * Redirects to positions.xhtml page.
     *
     * @return path to redirect
     */
    public String positions() {
        return View.POSITIONS.getPath();
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
