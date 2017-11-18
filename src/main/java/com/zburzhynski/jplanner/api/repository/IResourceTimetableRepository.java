package com.zburzhynski.jplanner.api.repository;

import com.zburzhynski.jplanner.api.domain.IDomain;

/**
 * Available resource timetable service.
 * <p/>
 * Date: 06.09.2015
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface IResourceTimetableRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

}
