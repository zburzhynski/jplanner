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
    CABINETS("/pages/cabinet/cabinets?faces-redirect=true"),
    CABINET("/pages/cabinet/cabinet?faces-redirect=true"),
    POSITIONS("/pages/position/positions?faces-redirect=true"),
    POSITION("/pages/position/position?faces-redirect=true"),
    PATIENTS("/pages/patient/patients?faces-redirect=true"),
    PATIENT_SEARCH("/pages/patient/search?faces-redirect=true");

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
