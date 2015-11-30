package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.CabinetSearchCriteria;
import com.zburzhynski.jplanner.api.repository.ICabinetRepository;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ICabinetService} interface.
 * <p/>
 * Date: 05.05.2015
 *
 * @author Mikalai Karabeika
 */
@Service("cabinetService")
@Transactional(readOnly = true)
public class CabinetService implements ICabinetService<String, Cabinet> {

    @Autowired
    private ICabinetRepository cabinetRepository;

    @Override
    public Cabinet getById(String id) {
        return (Cabinet) cabinetRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Cabinet cabinet) {
        boolean result = false;
        if (cabinet != null) {
            cabinetRepository.saveOrUpdate(cabinet);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Cabinet cabinet) {
        cabinetRepository.delete(cabinet);
    }

    @Override
    public List<Cabinet> getByCriteria(CabinetSearchCriteria searchCriteria) {
        return cabinetRepository.findByCriteria(searchCriteria);
    }

    @Override
    public int countByCriteria(CabinetSearchCriteria searchCriteria) {
        return cabinetRepository.countByCriteria(searchCriteria);
    }

    @Override
    public List<Cabinet> getAll() {
        return cabinetRepository.findAll();
    }

    @Override
    public boolean isUsed(Cabinet cabinet) {
        return cabinetRepository.isUsed(cabinet);
    }

}