package com.zburzhynski.jplanner.impl.rest.client;

import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.SearchVisitResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.JdentUnavailableException;
import com.zburzhynski.jplanner.impl.rest.exception.PatientNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleEventAlreadyExistException;

/**
 * Dental visit rest client interface.
 * <p/>
 * Date: 16.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public interface IVisitRestClient {

    /**
     * Gets dental visit by schedule id.
     *
     * @param scheduleId schedule id
     * @param jdentUrl   jdent url
     * @return {@link SearchVisitResponse} search dental visit response
     * @throws JdentUnavailableException if jdent service not available
     */
    SearchVisitResponse getByScheduleId(String scheduleId, String jdentUrl) throws JdentUnavailableException;

    /**
     * Creates visit.
     *
     * @param request  {@link CreateVisitRequest} create visit request
     * @param jdentUrl jdent url
     * @return {@link CreateVisitResponse}create visit response
     * @throws PatientNotFoundException           if patient not found
     * @throws EmployeeNotFoundException          if employee not found
     * @throws ScheduleEventAlreadyExistException if schedule event already exist
     * @throws JdentUnavailableException          if jdent service not available
     */
    CreateVisitResponse createVisit(CreateVisitRequest request, String jdentUrl)
        throws PatientNotFoundException, EmployeeNotFoundException,
        ScheduleEventAlreadyExistException, JdentUnavailableException;

}
