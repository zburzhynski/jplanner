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
    WORKPLACE("/pages/cabinet/workplace?faces-redirect=true"),
    POSITIONS("/pages/position/positions?faces-redirect=true"),
    POSITION("/pages/position/position?faces-redirect=true"),
    ORGANIZATION_TIMETABLES("/pages/organization-timetable/timetables?faces-redirect=true"),
    ORGANIZATION_TIMETABLE("/pages/organization-timetable/timetable?faces-redirect=true"),
    TIMETABLES("/pages/timetable/timetables?faces-redirect=true"),
    TIMETABLE("/pages/timetable/timetable?faces-redirect=true"),
    TIMETABLE_TEMPLATE("/pages/timetable/timetable-template?faces-redirect=true"),
    TIMETABLE_QUOTAS("/pages/timetable/timetable-quotas?faces-redirect=true"),
    TIMETABLE_QUOTA("/pages/timetable/timetable-quota?faces-redirect=true"),
    AVAILABLE_RESOURCES("/pages/available-resource/resources?faces-redirect=true"),
    AVAILABLE_RESOURCE("/pages/available-resource/resource?faces-redirect=true"),
    EMPLOYEES("/pages/employee/employees?faces-redirect=true"),
    EMPLOYEE("/pages/employee/employee?faces-redirect=true"),
    PATIENTS("/pages/patient/patients?faces-redirect=true"),
    PATIENT_SEARCH("/pages/patient/search?faces-redirect=true"),
    SETTINGS("/pages/setting/settings?faces-redirect=true"),
    SETTING("/pages/setting/setting?faces-redirect=true");

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
