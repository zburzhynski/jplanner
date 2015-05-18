package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.View;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

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

    /**
     * Redirects to schedules.xhtml page.
     *
     * @return path to redirect
     */
    public String schedules() {
        return View.SCHEDULE_EVENTS.getPath();
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

}
