package com.zburzhynski.jplanner.impl.converter;

import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

/**
 * Event converter.
 * <p/>
 * Date: 24.04.15
 *
 * @author Vladimir Zburzhynski
 */
public final class ScheduleConverter {

    private ScheduleConverter() {
        throw new AssertionError();
    }

    /**
     * Converts {@link ScheduleEvent} to {@link Schedule}.
     *
     * @param event {@link ScheduleEvent} schedule event to convert
     * @return {@link Schedule}
     */
    public static Schedule convert(ScheduleEvent event) {
        Schedule schedule = new Schedule();
        schedule.setId(event.getId());
        schedule.setStartDate(event.getStartDate());
        schedule.setEndDate(event.getEndDate());
        schedule.setTitle(event.getTitle());
        schedule.setDescription(event.getDescription());
        return schedule;
    }

    /**
     * Converts  {@link Schedule} to {@link ScheduleEvent}.
     *
     * @param event {@link Schedule} schedule to convert
     * @return {@link ScheduleEvent}
     */
    public static ScheduleEvent convert(Schedule event) {
        DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(event.getTitle(), event.getStartDate(), event.getEndDate());
        scheduleEvent.setId(event.getId());
        return scheduleEvent;
    }

}