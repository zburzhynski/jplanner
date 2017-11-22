package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.QuotaSearchCriteria;
import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.exception.LinkedTimetablesExistException;
import com.zburzhynski.jplanner.api.repository.IAvailableResourceRepository;
import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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

    @Autowired
    private IQuotaRepository quotaRepository;

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
    public void delete(AvailableResource resource) throws LinkedTimetablesExistException {
        if (CollectionUtils.isNotEmpty(resource.getTimetables())) {
            throw new LinkedTimetablesExistException();
        }
        availableResourceRepository.delete(resource);
    }

    @Override
    public List<AvailableResource> getByCriteria(AvailableResourceSearchCriteria searchCriteria) {
        if (searchCriteria.getQuotaStartDate() != null && searchCriteria.getQuotaEndDate() != null) {
            QuotaSearchCriteria quotaSearchCriteria = new QuotaSearchCriteria();
            quotaSearchCriteria.setStartDate(searchCriteria.getQuotaStartDate());
            quotaSearchCriteria.setEndDate(searchCriteria.getQuotaEndDate());
            quotaSearchCriteria.setDoctorId(searchCriteria.getDoctor().getId());
            quotaSearchCriteria.setIntersectingPeriod(true);
            List<Quota> intersectingQuotas = quotaRepository.findByCriteria(quotaSearchCriteria);
            if (CollectionUtils.isEmpty(intersectingQuotas)) {
                return new ArrayList<>();
            }
            for (Quota quota : intersectingQuotas) {
                if (!QuotaType.WORK_TIME.equals(quota.getQuotaType())) {
                    return new ArrayList<>();
                }
            }
            SortedSet<Quota> sortedQuotas = new TreeSet<>(intersectingQuotas);
            if (sortedQuotas.first().getStartDate().after(searchCriteria.getQuotaStartDate())
                || sortedQuotas.last().getEndDate().before(searchCriteria.getQuotaEndDate())) {
                return new ArrayList<>();
            }
            List<String> quotaIds = new ArrayList<>();
            for (Quota quota : intersectingQuotas) {
                quotaIds.add(quota.getId());
            }
            searchCriteria.setQuotaIds(quotaIds);
        }
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

}
