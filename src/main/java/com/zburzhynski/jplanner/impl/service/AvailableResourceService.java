package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.repository.IAvailableResourceRepository;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IAvailableResourceService} interface.
 * <p/>
 * Date: 17.10.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("availableResourceService")
@Transactional(readOnly = true)
public class AvailableResourceService implements IAvailableResourceService<String, AvailableResource> {

    @Autowired
    private IAvailableResourceRepository availableResourceRepository;

    @Override
    public AvailableResource getById(String id) {
        return (AvailableResource) availableResourceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(AvailableResource resource) {
        boolean result = false;
        if (resource != null) {
            if (StringUtils.isEmpty(resource.getId())) {
                availableResourceRepository.insert(resource);
                result = true;
            } else {
                availableResourceRepository.update(resource);
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(AvailableResource resource) {
        boolean deleted = false;
        if (resource != null) {
            availableResourceRepository.delete(resource);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<AvailableResource> getByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        return availableResourceRepository.findByCriteria(searchCriteria);
    }

    @Override
    public int countByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        return availableResourceRepository.countByCriteria(searchCriteria);
    }

    @Override
    public List<AvailableResource> getAll() {
        return availableResourceRepository.findAll();
    }

    @Override
    public boolean isUsed(AvailableResource resource) {
        return availableResourceRepository.isUsed(resource);
    }

}
