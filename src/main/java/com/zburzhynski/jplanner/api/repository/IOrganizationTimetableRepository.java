package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Organization timetable service.
 * <p/>
 * Date: 27.11.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IOrganizationTimetableRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {
}
