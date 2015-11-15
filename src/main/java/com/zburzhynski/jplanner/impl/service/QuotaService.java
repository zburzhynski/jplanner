package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.api.service.IQuotaService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Implementation of {@link IQuotaService} interface.
 * <p/>
 * Date: 02.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("quotaService")
@Transactional(readOnly = true)
public class QuotaService implements IQuotaService<String, Quota> {

    @Autowired
    private IQuotaRepository quotaRepository;

    @Override
    public Quota getById(String id) {
        return (Quota) quotaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Quota quota) {
        boolean result = false;
        if (quota != null) {
            quotaRepository.saveOrUpdate(quota);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Quota quota) {
        boolean deleted = false;
        if (quota != null) {
            quotaRepository.delete(quota);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<Quota> getIntersecting(Date startDate, Date endDate) {
        return quotaRepository.findIntersecting(startDate, endDate);
    }

    @Override
    public List<Quota> getAll() {
        return quotaRepository.findAll();
    }

}
