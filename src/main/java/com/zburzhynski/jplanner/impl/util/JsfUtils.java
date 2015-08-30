package com.zburzhynski.jplanner.impl.util;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * Class has common methods for working with jsf beans.
 * <p/>
 * Date: 4/27/2015
 *
 * @author Vladimir Zburzhynski
 */
public final class JsfUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsfUtils.class);

    private JsfUtils() {
        throw new AssertionError();
    }

    /**
     * Redirects to url.
     *
     * @param url url to redirect
     */
    public static void redirect(String url) {
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler nav = context.getApplication().getNavigationHandler();
        nav.handleNavigation(context, null, url);
        context.renderResponse();
    }

    /**
     * Redirects to external url.
     *
     * @param url external url to redirect
     */
    public static void externalRedirect(String url) {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(url);
        } catch (IOException e) {
            LOGGER.error("Can not redirect to url", url);
        }
    }

    /**
     * Gets bean from jsf session context by name.
     *
     * @param name bean name
     * @param <T>  bean  type
     * @return bean from context
     */
    public static <T> T getBean(String name) {
        return (T) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
    }

    /**
     * Force refresh page.
     */
    public static void refreshPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

    /**
     * Updates element state.
     *
     * @param name name of element to update
     */
    public static void update(String name) {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(name);
    }

    /**
     * Executes java script.
     *
     * @param script java script to execute
     */
    public static void execute(String script) {
        RequestContext.getCurrentInstance().execute(script);
    }

}
