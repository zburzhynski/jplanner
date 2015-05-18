package com.zburzhynski.jplanner.api.domain;

/**
 * Contains view pages.
 * <p/>
 * Date: 22.04.15
 *
 * @author Vladimir Zburzhynski
 */
public enum View {

    SCHEDULE_EVENTS("/pages/event/schedule-events?faces-redirect=true"),
    SCHEDULE_EVENT("/pages/event/schedule-event?faces-redirect=true"),
    ABOUT("/pages/help/about?faces-redirect=true"),
    CABINETS("/pages/directories/cabinets?faces-redirect=true"),
    CABINET("/pages/directories/cabinet?faces-redirect=true");

    private String path;

    /**
     * Constructor.
     *
     * @param path path
     */
    private View(String path) {
        this.path = path;
    }

    /**
     * Gets path.
     *
     * @return path
     */
    public String getPath() {
        return path;
    }

}
