package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.repository.IOrganizationTimetableRepository;
import com.zburzhynski.jplanner.api.service.IOrganizationTimetableService;
import com.zburzhynski.jplanner.impl.domain.OrganizationTimetable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IOrganizationTimetableService} interface.
 * <p/>
 * Date: 27.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("organizationTimetableService")
@Transactional(readOnly = true)
public class OrganizationTimetableService implements IOrganizationTimetableService<String, OrganizationTimetable> {

    @Autowired
    private IOrganizationTimetableRepository timetableRepository;

    @Override
    public OrganizationTimetable getById(String id) {
        return (OrganizationTimetable) timetableRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(OrganizationTimetable timetable) {
        boolean result = false;
        if (timetable != null) {
            if (StringUtils.isBlank(timetable.getId())) {
                timetableRepository.insert(timetable);
                result = true;
            } else {
                timetableRepository.update(timetable);
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(OrganizationTimetable timetable) {
        boolean deleted = false;
        if (timetable != null) {
            timetableRepository.delete(timetable);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<OrganizationTimetable> getAll() {
        return timetableRepository.findAll();
    }

}
