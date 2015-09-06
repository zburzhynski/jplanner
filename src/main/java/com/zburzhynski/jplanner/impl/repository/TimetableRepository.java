package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.repository.ITimetableRepository;
import com.zburzhynski.jplanner.impl.domain.Timetable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ITimetableRepository} interface.
 * <p/>
 * Date: 06.09.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository("timetableRepository")
public class TimetableRepository extends AbstractBaseRepository<String, Timetable>
    implements ITimetableRepository<Timetable> {

    @Override
    protected Class<? extends Timetable> getDomainClass() {
        return Timetable.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
