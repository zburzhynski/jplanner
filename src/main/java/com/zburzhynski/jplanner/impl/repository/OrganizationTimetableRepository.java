package com.zburzhynski.jplanner.impl.repository;

import com.zburzhynski.jplanner.api.repository.IOrganizationTimetableRepository;
import com.zburzhynski.jplanner.impl.domain.OrganizationTimetable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link IOrganizationTimetableRepository} interface.
 * <p/>
 * Date: 27.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@Repository("organizationTimetableRepository")
public class OrganizationTimetableRepository extends AbstractBaseRepository<String, OrganizationTimetable>
    implements IOrganizationTimetableRepository<String, OrganizationTimetable> {

    @Override
    protected Class<? extends OrganizationTimetable> getDomainClass() {
        return OrganizationTimetable.class;
    }

    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
