package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.repository.IWorkplaceRepository;
import com.zburzhynski.jplanner.api.service.IWorkplaceService;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IWorkplaceService} interface.
 * <p/>
 * Date: 03.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("workplaceService")
@Transactional(readOnly = true)
public class WorkplaceService implements IWorkplaceService<Workplace> {

    @Autowired
    private IWorkplaceRepository workplaceRepository;

    @Override
    public List<Workplace> getAll() {
        return workplaceRepository.getAll();
    }

}
