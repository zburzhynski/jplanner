package com.zburzhynski.jplanner.impl.jsf.bean;


import static com.zburzhynski.jplanner.api.domain.ScheduleStatus.CANCELED;
import static com.zburzhynski.jplanner.api.domain.ScheduleStatus.FINISHED;
import static com.zburzhynski.jplanner.api.domain.ScheduleStatus.PLANNED;
import com.zburzhynski.jplanner.api.domain.ScheduleStatus;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Event menu bean.
 * <p/>
 * Date: 30.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class EventMenuBean implements Serializable {

    @ManagedProperty(value = "#{scheduleBean}")
    private ScheduleBean scheduleBean;

    /**
     * Gets event menu header.
     *
     * @return event menu header
     */
    public String getMenuHeader() {
        if (scheduleBean.getEvent() != null && scheduleBean.getEvent().getPerson() != null
            && !scheduleBean.getEvent().getPerson().isEmpty()) {
            return scheduleBean.getEvent().getPerson().getShortName();
        }
        return null;
    }

    /**
     * Checks is start event button visible.
     *
     * @return true if visible, else false
     */
    public boolean isVisibleStartEventButton() {
        return PLANNED == getEventStatus();
    }

    /**
     * Checks is finish event button visible.
     *
     * @return true if visible, else false
     */
    public boolean isVisibleFinishEventButton() {
        return CANCELED != getEventStatus() && FINISHED != getEventStatus();
    }

    /**
     * Checks is cancel event button visible.
     *
     * @return true if visible, else false
     */
    public boolean isVisibleCancelEventButton() {
        return CANCELED != getEventStatus() && FINISHED != getEventStatus();
    }

    /**
     * Checks is go to dental card button visible.
     *
     * @return true if visible, else false
     */
    public boolean isVisibleGoToCardButton() {
        if (scheduleBean.getEvent() != null) {
            if (StringUtils.isNotBlank(scheduleBean.getEvent().getPatientId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets current schedule status.
     *
     * @return current schedule status
     */
    public ScheduleStatus getEventStatus() {
        return scheduleBean.getEvent() != null ? scheduleBean.getEvent().getStatus() : null;
    }

    /**
     * Sets schedule bean.
     *
     * @param scheduleBean {@link ScheduleBean} to set
     */
    public void setScheduleBean(ScheduleBean scheduleBean) {
        this.scheduleBean = scheduleBean;
    }

}
