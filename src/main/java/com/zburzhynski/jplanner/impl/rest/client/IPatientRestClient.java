package com.zburzhynski.jplanner.impl.rest.client;

import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitRequest;
import com.zburzhynski.jplanner.impl.rest.domain.CreateVisitResponse;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientResponse;
import com.zburzhynski.jplanner.impl.rest.exception.EmployeeNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.PatientNotFoundException;
import com.zburzhynski.jplanner.impl.rest.exception.ScheduleEventAlreadyExistException;

/**
 * Patient rest client interface.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
public interface IPatientRestClient {

    /**
     * Gets patient by criteria.
     *
     * @param request {@link SearchPatientRequest} search patient request
     * @return {@link SearchPatientResponse} search patient response
     */
    SearchPatientResponse getByCriteria(SearchPatientRequest request);

    /**
     * Creates visit.
     *
     * @param request {@link CreateVisitRequest} create visit request
     * @return {@link CreateVisitResponse}create visit response
     * @throws PatientNotFoundException           if patient not found
     * @throws EmployeeNotFoundException          if employee not found
     * @throws ScheduleEventAlreadyExistException if schedule event already exist
     */
    CreateVisitResponse createVisit(CreateVisitRequest request)
        throws PatientNotFoundException, EmployeeNotFoundException, ScheduleEventAlreadyExistException;

}
