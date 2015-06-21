package com.zburzhynski.jplanner.api.domain;

import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.JdentUnavailableException;
import com.zburzhynski.jplanner.impl.rest.exception.PatientNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleEventAlreadyExistException;

/**
 * Error.
 * <p/>
 * Date: 21.06.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum Error {

    PATIENT_NOT_FOUND_EXCEPTION("PatientNotFoundException", "error.patientNotFound"),

    EMPLOYEE_NOT_FOUND_EXCEPTION("EmployeeNotFoundException", "error.employeeNotFound"),

    SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION("ScheduleEventAlreadyExistException", "error.scheduleEventAlreadyExist"),

    JDENT_UNAVAILABLE_EXCEPTION("JdentUnavailableException", "error.jdentUnavailable");

    private String name;

    private String message;

    /**
     * Constructor.
     *
     * @param name    exception name
     * @param message localisation message
     */
    private Error(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * Throws exception by name.
     *
     * @param name exception name
     * @throws PatientNotFoundException           if patient not found
     * @throws ScheduleEventAlreadyExistException if schedule event already exist
     * @throws EmployeeNotFoundException          if employee not found
     * @throws JdentUnavailableException          if jdent service not available
     */
    public static void throwException(String name) throws PatientNotFoundException, ScheduleEventAlreadyExistException,
        EmployeeNotFoundException, JdentUnavailableException {
        if (PATIENT_NOT_FOUND_EXCEPTION.getName().equals(name)) {
            throw new PatientNotFoundException();
        } else if (SCHEDULE_EVENT_ALREADY_EXIST_EXCEPTION.getName().equals(name)) {
            throw new ScheduleEventAlreadyExistException();
        } else if (EMPLOYEE_NOT_FOUND_EXCEPTION.getName().equals(name)) {
            throw new EmployeeNotFoundException();
        } else if (JDENT_UNAVAILABLE_EXCEPTION.getName().equals(name)) {
            throw new JdentUnavailableException();
        }
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

}
